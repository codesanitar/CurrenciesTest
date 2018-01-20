package org.paramedic.homeless.currenciestest.service.data.repository;

import org.paramedic.homeless.currenciestest.service.data.response.RateResponse;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by codesanitar on 18/01/18.
 */

public interface RatesRepository {
    Flowable<List<RateEntity>> getContent();

    Flowable<List<ConvertibleRateEntity>> getConvertibleContent();

    void updateAmount(BaseAmount baseAmount);

    void updateRates(RateResponse rateResponse);

    Flowable<Boolean> swapBaseRate(int id);

    Flowable<RateEntity> getBaseEntity();
}
