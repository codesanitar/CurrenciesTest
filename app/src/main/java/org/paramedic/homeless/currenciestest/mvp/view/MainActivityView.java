package org.paramedic.homeless.currenciestest.mvp.view;

import org.paramedic.homeless.currenciestest.mvp.base.BaseView;

public interface MainActivityView extends BaseView {

    void ratesRequestCompleted(String s);

    void connectionError();

    void couldNotCompleteRequest();
}
