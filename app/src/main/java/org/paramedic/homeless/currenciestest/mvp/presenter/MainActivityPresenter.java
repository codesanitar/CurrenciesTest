package org.paramedic.homeless.currenciestest.mvp.presenter;


import org.paramedic.homeless.currenciestest.mvp.base.BaseActivityPresenter;
import org.paramedic.homeless.currenciestest.mvp.model.MainActivityModel;
import org.paramedic.homeless.currenciestest.mvp.view.MainActivityView;
import org.paramedic.homeless.currenciestest.service.api.exception.NoConnectivityException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.paramedic.homeless.currenciestest.StaticConfig.REQUEST_INTERVAL_SECONDS;


public class MainActivityPresenter extends BaseActivityPresenter<MainActivityView> {

    private final MainActivityModel mainActivityModel;
    private Disposable scheduleRatesRequest;
    private boolean successRequest = true;

    public MainActivityPresenter(MainActivityModel mainActivityModel) {
        this.mainActivityModel = mainActivityModel;
    }

    @Override
    protected void onFirstViewCreated() {
        super.onFirstViewCreated();
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
    }

    public void startRatesRequests() {
        stopRatesRequests();
        scheduleRatesRequest = Flowable.interval(0, REQUEST_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .onBackpressureDrop()
                .flatMapSingle(tick -> getModel().getBaseEntityName(), false, 1)
                .flatMapSingle(baseCurrency -> getModel().requestRates(baseCurrency), false, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (hasView()) {
                        getView().ratesRequestCompleted(s);
                    }
                    successRequest = true;
                }, throwable -> {
                    throwable.printStackTrace();
                    if (successRequest) {
                        if (throwable instanceof NoConnectivityException && hasView()) {
                            getView().connectionError();
                        } else {
                            if (hasView()) {
                                getView().couldNotCompleteRequest();
                            }
                        }
                        successRequest = false;
                    }
                    startRatesRequests();
                });
        getCompositeDisposable().add(scheduleRatesRequest);
    }

    public void stopRatesRequests() {
        if (scheduleRatesRequest != null) {
            getCompositeDisposable().remove(scheduleRatesRequest);
            scheduleRatesRequest = null;
        }
    }

    /**
     * @return model for base class
     */
    @Override
    protected MainActivityModel getModel() {
        return mainActivityModel;
    }

}
