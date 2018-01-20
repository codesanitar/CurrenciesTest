package org.paramedic.homeless.currenciestest.di.component;

import org.paramedic.homeless.currenciestest.di.module.CoreModule;
import org.paramedic.homeless.currenciestest.di.module.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                CoreModule.class
        }
)
public interface CoreComponent {
    MvpComponent plusModels(PresenterModule module);
}
