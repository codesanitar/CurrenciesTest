package org.paramedic.homeless.currenciestest.mvp.model;


import org.paramedic.homeless.currenciestest.di.scope.MvpScope;
import org.paramedic.homeless.currenciestest.mvp.base.BaseModel;
import org.paramedic.homeless.currenciestest.service.data.repository.RateEntity;
import org.paramedic.homeless.currenciestest.service.data.repository.RatesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

@MvpScope
public final class AllRatesFragmentModel extends BaseModel {
    /**
     * Simple fragment model
     */

    @Inject
    RatesRepository ratesRepository;

    @Inject
    public AllRatesFragmentModel() {
    }

    public Flowable<List<RateEntity>> getRates() {
        return ratesRepository.getContent();
    }

    public Flowable<Boolean> swapBaseRate(int id) {
        return ratesRepository.swapBaseRate(id);
    }
}
