package org.paramedic.homeless.currenciestest.mvp.model;


import org.paramedic.homeless.currenciestest.di.scope.MvpScope;
import org.paramedic.homeless.currenciestest.mvp.base.BaseModel;
import org.paramedic.homeless.currenciestest.service.api.DuckDNSClient;
import org.paramedic.homeless.currenciestest.service.data.repository.RatesRepository;
import org.paramedic.homeless.currenciestest.service.data.response.RateResponse;

import javax.inject.Inject;

import io.reactivex.Single;
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

    public Single<String> requestRates(String base) {
        return duckDNSClient.latestRates(base)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(this::writeToDB)
                ;
    }

    private String writeToDB(RateResponse rateResponse) {
        ratesRepository.saveRates(rateResponse);
        return rateResponse.getDate();
    }

    public Single<String> getBaseEntityName() {
        return ratesRepository.getBaseEntityName();
    }
}
