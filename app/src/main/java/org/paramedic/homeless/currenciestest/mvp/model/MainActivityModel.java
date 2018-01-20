package org.paramedic.homeless.currenciestest.mvp.model;


import org.paramedic.homeless.currenciestest.di.scope.MvpScope;
import org.paramedic.homeless.currenciestest.mvp.base.BaseModel;
import org.paramedic.homeless.currenciestest.service.api.DuckDNSClient;
import org.paramedic.homeless.currenciestest.service.data.response.RateResponse;
import org.paramedic.homeless.currenciestest.service.data.repository.RateEntity;
import org.paramedic.homeless.currenciestest.service.data.repository.RatesRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

@MvpScope
public final class MainActivityModel extends BaseModel {

    @Inject
    RatesRepository ratesRepository;
    @Inject
    DuckDNSClient duckDNSClient;

    /**
     * Simple activity model
     */
    @Inject
    public MainActivityModel() {
    }

    public Flowable<String> requestRates(String base) {
        return duckDNSClient.latestRates(base)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(this::writeToDB)
                ;
    }

    private String writeToDB(RateResponse rateResponse) {
        ratesRepository.updateRates(rateResponse);
        return rateResponse.getDate();
    }

    public Flowable<RateEntity> getBaseEntity() {
        return ratesRepository.getBaseEntity();
    }
}
