package org.paramedic.homeless.currenciestest.di.module;

import android.support.annotation.NonNull;

import org.paramedic.homeless.currenciestest.mvp.base.loader.PresenterFactory;
import org.paramedic.homeless.currenciestest.mvp.model.AllRatesFragmentModel;
import org.paramedic.homeless.currenciestest.mvp.model.ConverterFragmentModel;
import org.paramedic.homeless.currenciestest.mvp.model.MainActivityModel;
import org.paramedic.homeless.currenciestest.mvp.presenter.AllRatesFragmentPresenter;
import org.paramedic.homeless.currenciestest.mvp.presenter.ConverterFragmentPresenter;
import org.paramedic.homeless.currenciestest.mvp.presenter.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    public PresenterFactory<MainActivityPresenter> provideMainActivityPresenterFactory(@NonNull final MainActivityModel model) {
        return () -> new MainActivityPresenter(model);
    }

    @Provides
    public PresenterFactory<ConverterFragmentPresenter> provideConverterFragmentPresenterPresenterFactory(@NonNull final ConverterFragmentModel model) {
        return () -> new ConverterFragmentPresenter(model);
    }

    @Provides
    public PresenterFactory<AllRatesFragmentPresenter> provideAllRatesFragmentPresenterPresenterFactory(@NonNull final AllRatesFragmentModel model) {
        return () -> new AllRatesFragmentPresenter(model);
    }
}
