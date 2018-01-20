package org.paramedic.homeless.currenciestest.mvp.base.loader;

import android.support.annotation.NonNull;

import org.paramedic.homeless.currenciestest.mvp.base.BasePresenter;


/**
 * Factory to implement to create a presenter
 */
@FunctionalInterface
public interface PresenterFactory<T extends BasePresenter> {
    @NonNull
    T create();
}
