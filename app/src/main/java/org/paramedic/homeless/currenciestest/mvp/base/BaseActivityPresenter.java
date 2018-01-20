package org.paramedic.homeless.currenciestest.mvp.base;


public abstract class BaseActivityPresenter<V extends BaseView>
        extends BasePresenterImpl<V>
{

    protected final String TAG = this.getClass().getSimpleName();
    private boolean destroyed = false;
    private boolean viewAttachedFirstTime = false;

    abstract protected BaseModel getModel();

    /**
     * is activity resumed
     */
    private boolean resumed;

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void onPresenterDestroyed() {
        destroyed = true;
        super.onPresenterDestroyed();
    }

    protected V getView() {
        return mView;
    }

    protected void onFirstViewCreated() {

    }

    @Override
    public void onStart(boolean viewCreated) {
        if (viewCreated && !viewAttachedFirstTime) {
            viewAttachedFirstTime = true;
            onFirstViewCreated();
        }
        if (viewCreated) {
            onViewCreated();
        }
        super.onStart(viewCreated);
    }

    protected void onViewCreated() {

    }

    public boolean isResumed() {
        return resumed;
    }
}
