package org.paramedic.homeless.currenciestest;

import android.app.Application;

import org.paramedic.homeless.currenciestest.di.component.CoreComponent;
import org.paramedic.homeless.currenciestest.di.component.DaggerCoreComponent;
import org.paramedic.homeless.currenciestest.di.component.MvpComponent;
import org.paramedic.homeless.currenciestest.di.module.CoreModule;
import org.paramedic.homeless.currenciestest.di.module.PresenterModule;

/**
 * Created by codesanitar on 16/01/18.
 */

public class RevApplication extends Application implements MvpComponentProvider {

    private CoreComponent coreComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger(this);
    }

    private void initDagger(Application application) {
        coreComponent = DaggerCoreComponent.builder()
                .coreModule(new CoreModule(application))
                .build();
    }

    public MvpComponent generateMvpComponent() {
        return coreComponent.plusModels(new PresenterModule());
    }
}
