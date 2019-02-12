package org.paramedic.homeless.currenciestest.service.data.repository;

import org.paramedic.homeless.currenciestest.service.data.response.RateResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by codesanitar on 12/02/19.
 */

public interface RatesRepository {

    Flowable<List<RateEntity>> getRates();

    Flowable<List<RateEntity>> getRatesWithAmount();

    void saveBaseAmount(BaseAmount baseAmount);

    void saveRates(RateResponse rateResponse);

    boolean saveBaseEntityRate(int id);

    Single<String> getBaseEntityName();
}
