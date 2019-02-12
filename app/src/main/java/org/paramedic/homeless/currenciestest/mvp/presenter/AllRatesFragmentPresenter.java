package org.paramedic.homeless.currenciestest.mvp.presenter;

import org.paramedic.homeless.currenciestest.mvp.base.BaseFragmentPresenter;
import org.paramedic.homeless.currenciestest.mvp.model.AllRatesFragmentModel;
import org.paramedic.homeless.currenciestest.mvp.view.AllRatesFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class AllRatesFragmentPresenter extends BaseFragmentPresenter<AllRatesFragmentView> {

    private final AllRatesFragmentModel allRatesFragmentModel;
    private boolean recyclerInitialized = false;
    private Disposable contentSubscription;

    public AllRatesFragmentPresenter(AllRatesFragmentModel allRatesFragmentModel) {
        this.allRatesFragmentModel = allRatesFragmentModel;
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
        if (contentSubscription != null) {
            getCompositeDisposable().remove(contentSubscription);
        }
        contentSubscription = getModel().getRates()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(rateEntities -> {
                        if (hasView()) {
                            if(!recyclerInitialized) {
                                recyclerInitialized = true;
                                getView().initRecycler(rateEntities);
                            } else {
                                getView().refreshContent(rateEntities);
                            }
                        }
                    }
                    , Throwable::printStackTrace);
        getCompositeDisposable().add(contentSubscription);
    }

    /**
     * @return model for base class
     */
    @Override
    protected AllRatesFragmentModel getModel() {
        return allRatesFragmentModel;
    }

    public void swapBaseRate(int itemId) {
        getModel().swapBaseRate(itemId);
    }
}
