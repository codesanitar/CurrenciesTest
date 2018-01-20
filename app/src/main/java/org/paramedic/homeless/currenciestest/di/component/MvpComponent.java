package org.paramedic.homeless.currenciestest.di.component;

import org.paramedic.homeless.currenciestest.di.module.PresenterModule;
import org.paramedic.homeless.currenciestest.di.scope.MvpScope;
import org.paramedic.homeless.currenciestest.ui.activity.MainActivity;
import org.paramedic.homeless.currenciestest.ui.fragment.AllRatesFragment;
import org.paramedic.homeless.currenciestest.ui.fragment.ConverterFragment;

import dagger.Subcomponent;

@MvpScope
@Subcomponent(modules = {
        PresenterModule.class
} )
public interface MvpComponent {
    void inject(MainActivity mainActivity);

    void inject(ConverterFragment converterFragment);

    void inject(AllRatesFragment allRatesFragment);
}