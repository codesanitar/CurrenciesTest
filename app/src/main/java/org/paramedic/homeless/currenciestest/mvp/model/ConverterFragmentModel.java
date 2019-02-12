package org.paramedic.homeless.currenciestest.mvp.model;


import org.paramedic.homeless.currenciestest.di.scope.MvpScope;
import org.paramedic.homeless.currenciestest.mvp.base.BaseModel;
import org.paramedic.homeless.currenciestest.service.data.repository.BaseAmount;
import org.paramedic.homeless.currenciestest.service.data.repository.RateEntity;
import org.paramedic.homeless.currenciestest.service.data.repository.RatesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

@MvpScope
public final class ConverterFragmentModel extends BaseModel {
    /**
     * Simple fragment model
     */

    @Inject
    RatesRepository ratesRepository;

    @Inject
    public ConverterFragmentModel() {
    }

    public Flowable<List<RateEntity>> getContent() {
        return ratesRepository.getRatesWithAmount();
    }

    public void swapBaseRate(int id) {
        ratesRepository.saveBaseEntityRate(id);
    }

    public void updateBaseAmount(BaseAmount baseAmount) {
        ratesRepository.saveBaseAmount(baseAmount);
    }
}
