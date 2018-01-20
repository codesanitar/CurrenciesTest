package org.paramedic.homeless.currenciestest.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.Toast;

import org.paramedic.homeless.currenciestest.R;
import org.paramedic.homeless.currenciestest.di.component.MvpComponent;
import org.paramedic.homeless.currenciestest.mvp.base.RevBaseActivity;
import org.paramedic.homeless.currenciestest.mvp.base.loader.PresenterFactory;
import org.paramedic.homeless.currenciestest.mvp.presenter.MainActivityPresenter;
import org.paramedic.homeless.currenciestest.mvp.view.MainActivityView;
import org.paramedic.homeless.currenciestest.ui.adapter.MainActivityPagerAdapter;

import javax.inject.Inject;

public class MainActivity extends RevBaseActivity<MainActivityView, MainActivityPresenter> implements MainActivityView
{

    @Inject
    PresenterFactory<MainActivityPresenter> mPresenterFactory;

    @Override
    public MainActivityPresenter getPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected PresenterFactory<MainActivityPresenter> getPresenterFactory()
    {
        return mPresenterFactory;
    }

    private ViewPager viewPagerContainer;
    private TabLayout tabs;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorMain);
        viewPagerContainer = findViewById(R.id.view_pager_container);
        tabs = findViewById(R.id.tabs);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.header_rates_conversions);
        }
        initTabs();
    }

    @Override
    protected void injectDependencies(MvpComponent mvpComponent) {
        mvpComponent.inject(this);
    }

    private void initTabs() {
        viewPagerContainer.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), getBaseContext()));
        tabs.setupWithViewPager(viewPagerContainer);
        tabs.invalidate();
    }

    @Override
    public void ratesRequestCompleted(String s) {
        Log.v(TAG, s);
    }

    @Override
    public void connectionError() {
        Toast.makeText(this, "connection error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void couldNotCompleteRequest() {
        Toast.makeText(this, "could not complete request", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().stopRatesRequests();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().startRatesRequests();
    }
}
