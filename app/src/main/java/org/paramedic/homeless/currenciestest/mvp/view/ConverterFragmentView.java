package org.paramedic.homeless.currenciestest.mvp.view;

import org.paramedic.homeless.currenciestest.mvp.base.BaseView;
import org.paramedic.homeless.currenciestest.service.data.repository.RateEntity;

import java.util.List;

public interface ConverterFragmentView extends BaseView {

    void refreshContent(List<RateEntity> rateEntities);

    void initRecycler(List<RateEntity> recentRates);
}
