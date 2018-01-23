package org.paramedic.homeless.currenciestest.service.data.repository;

import android.util.Log;

import com.google.gson.JsonElement;

import org.paramedic.homeless.currenciestest.service.data.response.RateResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;

import static java.math.MathContext.DECIMAL128;
import static org.paramedic.homeless.currenciestest.StaticConfig.DEFAULT_ROUNDING_MODE;

/**
 * Created by codesanitar on 17/01/18.
 */

public class RatesRepositoryImpl implements RatesRepository {

    final static String TAG = "RatesRepositoryImpl";

    private final BehaviorProcessor<List<RateEntity>> processorRateEntity = BehaviorProcessor.create();
    private final BehaviorProcessor<BaseAmount> processorBaseAmount = BehaviorProcessor.create();

    @Override
    public Flowable<List<RateEntity>> getContent() {
        return processorRateEntity;
    }

    @Override
    public Flowable<RateEntity> getBaseEntity() {
        return processorRateEntity
                .map(this::getBaseFromList)
                .filter(rateEntity -> rateEntity != null)
                ;
    }

    private RateEntity getBaseFromList(List<RateEntity> rateEntities) {
        for (RateEntity rateEntity: rateEntities
             ) {
            if (rateEntity.isBase()) {
                return rateEntity;
            }
        }
        return null;
    }

    @Override
    public Flowable<List<ConvertibleRateEntity>> getConvertibleContent() {
         return Flowable.combineLatest(processorRateEntity, processorBaseAmount, this::makeConvertibleRates);
    }

    private List<ConvertibleRateEntity> makeConvertibleRates(List<RateEntity> rateEntities, BaseAmount baseAmount) {
        RateEntity thisRateEntity = null;
        for (RateEntity rateEntity:rateEntities
                ) {
            if (rateEntity.getId() == baseAmount.getId()) {
                thisRateEntity = rateEntity;
                break;
            }
        }
        List<ConvertibleRateEntity> convertibleRateEntities = new ArrayList<>();
        if (thisRateEntity == null) {
            return convertibleRateEntities;
        }

        BigDecimal amount;
        try {
            amount =  baseAmount.getValue().divide(thisRateEntity.getValue(), DECIMAL128);
        } catch (Exception e) {
            e.printStackTrace();
            return convertibleRateEntities;
        }

        ConvertibleRateEntityFactory<ConvertibleRateEntity> entityFactory = ConvertibleRateEntity::new;
        for (RateEntity rateEntity : rateEntities) {
            ConvertibleRateEntity convertibleRateEntity = entityFactory.create(rateEntity);
            convertibleRateEntity.setAmount(amount.multiply(convertibleRateEntity.getValue()).setScale(2, DEFAULT_ROUNDING_MODE).toPlainString());
            convertibleRateEntities.add(convertibleRateEntity);
        }
        return convertibleRateEntities;
    }

    @Override
    public void updateAmount(BaseAmount baseAmount) {
        synchronized (processorBaseAmount) {
            processorBaseAmount.onNext(baseAmount);
        }
    }

    @Override
    public void updateRates(RateResponse rateResponse) {
        RateEntityFactory<RateEntity> rateEntityRateEntityFactory = RateEntity::new;
        RateEntity firstEntity = rateEntityRateEntityFactory.create(rateResponse.getBase());

        synchronized (processorRateEntity) {
            if (processorRateEntity.hasValue()) {
                final List<RateEntity> tempList = new ArrayList<>();
                tempList.addAll(processorRateEntity.getValue());
                RateEntity baseEntity = null;
                for (RateEntity rateEntity : tempList
                        ) {
                    if (rateEntity.isBase()) {
                        baseEntity = rateEntity;
                        break;
                    }
                }
                if (baseEntity != null && baseEntity.getId() != firstEntity.getId()) {
                    //Well, we waiting for another base
                    return;
                }
            }

            List<RateEntity> rates = new ArrayList<>();
            if (firstEntity != null) {
                firstEntity.setValue(new BigDecimal("1"));
                firstEntity.setBase(true);
                rates.add(firstEntity);
                synchronized (processorBaseAmount) {
                    if (!processorBaseAmount.hasValue()) {
                        final BaseAmount baseAmount = new BaseAmount();
                        baseAmount.setValue(new BigDecimal("0"));
                        baseAmount.setId(firstEntity.getId());
                        processorBaseAmount.onNext(baseAmount);
                    }
                }
            }

            for (Map.Entry<String, JsonElement> entry : rateResponse.getRates().entrySet()) {
                RateEntity entity = rateEntityRateEntityFactory.create(entry.getKey());
                if (entity != null) {
                    try {
                        entity.setValue(new BigDecimal(entry.getValue().getAsString()));
                    } catch (Exception e) {
                        entity.setValue(new BigDecimal("1"));
                    }
                    rates.add(entity);
                }
            }

            processorRateEntity.onNext(rates);
        }
    }

    @Override
    public Flowable<Boolean> swapBaseRate(int id) {
        return Flowable.create(e -> {
            e.onNext(doSwapBaseRate(id));
            e.onComplete();
        }, BackpressureStrategy.DROP);
    }

    private boolean doSwapBaseRate(int id) {
        Log.v(TAG, String.valueOf(id));
        synchronized (processorRateEntity) {
            if (processorRateEntity.hasValue()) {
                final List<RateEntity> swapList = processorRateEntity.getValue();

                Log.v(TAG, String.valueOf(id) + " size of array" + String.valueOf(swapList.size()));
                int baseId = -1;
                int nextBaseId = -1;
                for (int i = 0; i < swapList.size(); i++) {
                    if (baseId == -1 && swapList.get(i).isBase()) {
                        baseId = i;
                    }
                    if (nextBaseId == -1 && swapList.get(i).getId() == id) {
                        nextBaseId = i;
                    }
                    if (baseId != -1 && nextBaseId != -1) {
                        break;
                    }
                }
                if (baseId != -1 && nextBaseId != -1 && baseId != nextBaseId) {
                    Log.v(TAG, String.valueOf(id) + " baseId" + String.valueOf(baseId) + " nextBaseId" + String.valueOf(nextBaseId));
                    RateEntity baseEntity = swapList.get(baseId);
                    RateEntity nextBaseEntity = swapList.get(nextBaseId);
                    baseEntity.setBase(false);
                    final BigDecimal crossValue = nextBaseEntity.getValue();
                    nextBaseEntity.setValue(new BigDecimal("1"));
                    nextBaseEntity.setBase(true);
                    swapList.set(baseId, nextBaseEntity);
                    swapList.remove(nextBaseId);


                    for (int i = 1; i < swapList.size(); i++) {
                        if (swapList.get(i).getId() > baseEntity.getId()) {
                            swapList.add(i, baseEntity);
                            break;
                        } else {
                            if (i == swapList.size() - 1) {
                                swapList.add(baseEntity);
                                break;
                            }
                        }
                    }

                    try {
                        for (int i = 0; i < swapList.size(); i++) {
                            if (i != baseId) {
                                swapList.get(i).setValue(swapList.get(i).getValue().divide(crossValue, DECIMAL128));
                            }
                        }
                    } catch (Exception e) {
                        Log.v(TAG, String.valueOf(id) + " exception");
                        return false;
                    }
                    Log.v(TAG, String.valueOf(id) + " goesOnNext");
                    processorRateEntity.onNext(swapList);
                    Log.v(TAG, String.valueOf(id) + " finish successful");
                    return true;
                } else {
                    if (baseId == nextBaseId) {
                        Log.v(TAG, String.valueOf(id) + " base equals!!!!");
                    } else {
                        Log.v(TAG, String.valueOf(id) + " not found value!!!!");
                    }
                }

            }
        }
            Log.v(TAG, String.valueOf(id) + " no value!!!!");
            return false;
    }
}
