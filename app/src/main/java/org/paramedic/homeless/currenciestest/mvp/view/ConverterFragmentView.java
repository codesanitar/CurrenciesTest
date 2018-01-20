package org.paramedic.homeless.currenciestest.mvp.view;

import org.paramedic.homeless.currenciestest.mvp.base.BaseView;
import org.paramedic.homeless.currenciestest.service.data.repository.ConvertibleRateEntity;

import java.util.List;

public interface ConverterFragmentView extends BaseView {

    void refreshContent(List<ConvertibleRateEntity> rateEntities);

    void initRecycler(List<ConvertibleRateEntity> recentRates);
}
