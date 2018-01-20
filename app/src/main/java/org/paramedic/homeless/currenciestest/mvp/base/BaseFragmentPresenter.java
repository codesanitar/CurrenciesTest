package org.paramedic.homeless.currenciestest.mvp.base;

/**
 * Created by codesanitar on 17/01/18.
 */

public abstract class BaseFragmentPresenter <V extends BaseView>
        extends BasePresenterImpl<V>
{
    private boolean resumed;
    private long resumeTime = System.currentTimeMillis();
    private boolean destroyed = false;
    private boolean viewAttachedFirstTime = false;

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void onPresenterDestroyed() {
        destroyed = true;
        super.onPresenterDestroyed();
    }

    protected void onFirstViewCreated() {

    }

    protected V getView() {
        return mView;
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

    protected abstract BaseModel getModel();
}
