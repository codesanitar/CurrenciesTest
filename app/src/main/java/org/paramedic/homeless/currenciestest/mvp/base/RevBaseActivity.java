package org.paramedic.homeless.currenciestest.mvp.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import org.paramedic.homeless.currenciestest.R;

public abstract class RevBaseActivity<V extends BaseView, P extends BaseActivityPresenter<V>>
        extends BaseActivity<P,V>
{
    protected final String TAG = this.getClass().getSimpleName();

    abstract protected CoordinatorLayout getCoordinatorLayout();

    public abstract P getPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initToolBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(view -> onBackPressed());
        }

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(true);
        }

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
    }

}
