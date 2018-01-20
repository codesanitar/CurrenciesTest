package org.paramedic.homeless.currenciestest.mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import org.paramedic.homeless.currenciestest.MvpComponentProvider;
import org.paramedic.homeless.currenciestest.di.component.MvpComponent;
import org.paramedic.homeless.currenciestest.mvp.base.loader.PresenterFactory;
import org.paramedic.homeless.currenciestest.mvp.base.loader.PresenterLoader;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseActivity<P extends BasePresenter<V>, V> extends AppCompatActivity implements LoaderManager.LoaderCallbacks<P> {
    /**
     * Do we need to call {@link #doStart()} from the {@link #onLoadFinished(Loader, BasePresenter)} method.
     * Will be true if presenter wasn't loaded when {@link #onStart()} is reached
     */
    private final AtomicBoolean mNeedToCallStart = new AtomicBoolean(false);
    /**
     * The presenter for this view
     */
    @Nullable
    protected P mPresenter;
    /**
     * Is this the first start of the activity (after onCreate)
     */
    private boolean mFirstStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependencies(getMvpComponent());

        super.onCreate(savedInstanceState);

        mFirstStart = true;

        getSupportLoaderManager().initLoader(0, null, this).startLoading();
    }

    /**
     * Event for
     * @param mvpComponent dependency injection
     */
    protected abstract void injectDependencies(MvpComponent mvpComponent);

    /**
     * @return mvpComponent
     */
    private MvpComponent getMvpComponent() {
        if (getApplication() instanceof MvpComponentProvider) {
            return ((MvpComponentProvider) getApplication()).generateMvpComponent();
        }
        throw new RuntimeException(getApplication().toString()
                    + " must implement MvpComponentProvider");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mPresenter == null) {
            mNeedToCallStart.set(true);
        } else {
            doStart();
        }
    }

    /**
     * Call the presenter callbacks for onStart
     */
    @SuppressWarnings("unchecked")
    private void doStart() {
        assert mPresenter != null;

        mPresenter.onViewAttached((V) this);

        mPresenter.onStart(mFirstStart);

        mFirstStart = false;
    }

    @Override
    protected void onStop() {
        if (mPresenter != null) {
            mPresenter.onStop();

            mPresenter.onViewDetached();
        }

        super.onStop();
    }

    @Override
    public final Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, getPresenterFactory());
    }

    @Override
    public final void onLoadFinished(Loader<P> loader, P presenter) {
        mPresenter = presenter;

        if (mNeedToCallStart.compareAndSet(true, false)) {
            doStart();
        }
    }

    @Override
    public final void onLoaderReset(Loader<P> loader) {
        mPresenter = null;
    }

    /**
     * Get the presenter factory implementation for this view
     *
     * @return the presenter factory
     */
    @NonNull
    protected abstract PresenterFactory<P> getPresenterFactory();
}
