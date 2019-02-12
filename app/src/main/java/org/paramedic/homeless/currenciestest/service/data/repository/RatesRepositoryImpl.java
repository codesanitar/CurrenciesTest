package org.paramedic.homeless.currenciestest.service.data.repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.paramedic.homeless.currenciestest.service.data.Currency;
import org.paramedic.homeless.currenciestest.service.data.response.RateResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static java.math.MathContext.DECIMAL128;
import static org.paramedic.homeless.currenciestest.StaticConfig.DEFAULT_ROUNDING_MODE;

/**
 * Created by codesanitar on 12/02/19.
 */

public class RatesRepositoryImpl implements RatesRepository {

    private final static String TAG = "RatesRepositoryImpl";
    private static final String TABLE_RATES = "tableRates";
    private static final String VALUE_BASE_ENTITY = "valueBaseEntity";
    private static final String VALUE_BASE_AMOUNT = "valueBaseAmount";
    private final Database database;

    public RatesRepositoryImpl(Context context,  Gson gson) {
        database = new Database(context, gson);
    }

    @Override
    public Flowable<List<RateEntity>> getRates() {
        return database.onChangeObjectSubscription(TABLE_RATES, new TypeToken<ArrayList<RateEntity>>(){}.getType(), true, Collections.emptyList());
    }

    @Override
    public Flowable<List<RateEntity>> getRatesWithAmount() {
        Flowable<List<RateEntity>> ratesChanges =
                database.onChangeObjectSubscription(TABLE_RATES, new TypeToken<ArrayList<RateEntity>>(){}.getType(), true, Collections.emptyList());
        Flowable<BaseAmount> baseAmountChanges =
                database.onChangeObjectSubscription(VALUE_BASE_AMOUNT, BaseAmount.class, true, new BaseAmount(32, new BigDecimal("0")));
        return Flowable.combineLatest(ratesChanges, baseAmountChanges, this::calcAmount);
    }

    private List<RateEntity> calcAmount(List<RateEntity> rates, BaseAmount baseAmount) {
        RateEntity baseAmountEntity = null;
        for (RateEntity rateEntity:rates
             ) {
            if (rateEntity.getCurrency().getId() == baseAmount.getId()) {
                baseAmountEntity = rateEntity;
                break;
            }
        }

        if (baseAmountEntity != null) {
            BigDecimal amountDiv = baseAmount.getValue().divide(baseAmountEntity.getValue(), DECIMAL128);
            for (RateEntity rateEntity : rates
                    ) {
                rateEntity.setAmount(amountDiv.multiply(rateEntity.getValue()).setScale(2, DEFAULT_ROUNDING_MODE).toPlainString());
            }
        }
        return rates;
    }

    @Override
    public Single<String> getBaseEntityName() {
        return Single.just(database.getString(VALUE_BASE_ENTITY, Currency.EUR.name()));
    }

    @Override
    public void saveBaseAmount(BaseAmount baseAmount) {
        database.putObject("valueBaseAmount", baseAmount);

    }

    @Override
    public void saveRates(RateResponse rateResponse) {
        if (rateResponse == null) {
            //Nothing to save
            return;
        }
        String storedBase = database.getString(VALUE_BASE_ENTITY, Currency.EUR.name());
        if (!storedBase.equals(rateResponse.getBase())) {
            //Well, we requests another base
            return;
        }

        final List<RateEntity> rates = new ArrayList<>();
        RateEntity firstEntity = new RateEntity(Currency.valueOf(storedBase));
        firstEntity.setValue(new BigDecimal("1"));
        rates.add(firstEntity);
        BaseAmount baseAmount = database.getObject(VALUE_BASE_AMOUNT, BaseAmount.class, null);
        if (baseAmount == null) {
            baseAmount = new BaseAmount(Currency.valueOf(storedBase).getId(), new BigDecimal("0"));
            database.putObject(VALUE_BASE_AMOUNT, baseAmount);
        }

        for (Map.Entry<String, JsonElement> entry : rateResponse.getRates().entrySet()) {
            RateEntity entity = new RateEntity(Currency.valueOf(entry.getKey()));
            try {
                entity.setValue(new BigDecimal(entry.getValue().getAsString()));
            } catch (Exception e) {
                entity.setValue(new BigDecimal("1"));
            }
            rates.add(entity);
        }

        database.putObject(TABLE_RATES, rates);
    }

    @Override
    public boolean saveBaseEntityRate(int id) {
        Log.v(TAG, String.valueOf(id));
        Currency storedBase = Currency.valueOf(database.getString(VALUE_BASE_ENTITY, Currency.EUR.name()));
        if (id == storedBase.getId()) {
            //same base
            return false;
        }
        List<RateEntity> list = database.getObject(TABLE_RATES, new TypeToken<ArrayList<RateEntity>>(){}.getType(), null);
        if (list == null) {
            //empty list
            return false;
        }
        RateEntity nextBaseEntity = null;
        for (RateEntity rateEntity:list
             ) {
            if (rateEntity.getCurrency().getId() == id) {
                nextBaseEntity = rateEntity;
                list.remove(rateEntity);
                break;
            }
        }
        if (nextBaseEntity == null) {
            //not found next base in list
            return false;
        }
        final BigDecimal crossValue = nextBaseEntity.getValue();
        nextBaseEntity.setValue(new BigDecimal("1"));
        Collections.sort(list, (o1, o2) -> o1.getCurrency().getId() - o2.getCurrency().getId());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setValue(list.get(i).getValue().divide(crossValue, DECIMAL128));
        }
        list.add(0, nextBaseEntity);
        database.putString(VALUE_BASE_ENTITY, nextBaseEntity.getCurrency().name());
        database.putObject(TABLE_RATES, list);
        return true;
    }

}
