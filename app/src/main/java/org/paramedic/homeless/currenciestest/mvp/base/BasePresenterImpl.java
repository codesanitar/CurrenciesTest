package org.paramedic.homeless.currenciestest.mvp.base;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Abstract presenter implementation that contains base implementation for other presenters.
 * Subclasses must call super for all {@link BasePresenter} method overriding.
 */
public abstract class BasePresenterImpl<V> implements BasePresenter<V> {
    /**
     * The view
     */

    private CompositeDisposable compositeDisposable;


    protected CompositeDisposable getCompositeDisposable(){
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    V mView;

    @Override
    public void onViewAttached(V view) {
        mView = view;
    }


    @Override
    public void onStart(boolean viewCreated) {

    }

    @Override
    public void onStop() {

    }

    protected boolean hasView() {
        return mView != null;
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onPresenterDestroyed() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

}
