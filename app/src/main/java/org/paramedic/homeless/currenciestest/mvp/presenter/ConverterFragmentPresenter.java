package org.paramedic.homeless.currenciestest.mvp.presenter;

import org.paramedic.homeless.currenciestest.StringUtils;
import org.paramedic.homeless.currenciestest.mvp.base.BaseFragmentPresenter;
import org.paramedic.homeless.currenciestest.mvp.model.ConverterFragmentModel;
import org.paramedic.homeless.currenciestest.mvp.view.ConverterFragmentView;
import org.paramedic.homeless.currenciestest.service.data.repository.BaseAmount;

import java.math.BigDecimal;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ConverterFragmentPresenter extends BaseFragmentPresenter<ConverterFragmentView> {

    private final ConverterFragmentModel converterFragmentModel;
    private boolean recyclerInitialized = false;

    public ConverterFragmentPresenter(ConverterFragmentModel converterFragmentModel) {
        this.converterFragmentModel = converterFragmentModel;
    }

    @Override
    protected void onFirstViewCreated() {
        super.onFirstViewCreated();
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        recyclerInitialized = false;
        //subscribe to Rates changes
        getCompositeDisposable().add(
                getModel().getContent()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(rateEntities -> {
                        if (hasView()) {
                            if (!recyclerInitialized) {
                                recyclerInitialized = true;
                                getView().initRecycler(rateEntities);
                            } else {
                                getView().refreshContent(rateEntities);
                            }
                        }
                    }
                    , Throwable::printStackTrace)
        );
    }

    /**
     * @return model for base class
     */
    @Override
    protected ConverterFragmentModel getModel() {
        return converterFragmentModel;
    }

    public void swapBaseRate(int itemId) {
        getModel().swapBaseRate(itemId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                            //call successful
                        }
                        , Throwable::printStackTrace
                );
    }

    public void updateBaseAmount(int itemId, String value) {
        if (!StringUtils.isNumeric(value)) {
            return;
        }
        BaseAmount baseAmount = new BaseAmount();
        baseAmount.setId(itemId);
        try {
            baseAmount.setValue(new BigDecimal(value));
        } catch (Exception e) {
            baseAmount.setValue(new BigDecimal("0"));
        }
        getModel().updateBaseAmount(baseAmount);
    }
}
