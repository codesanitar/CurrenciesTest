package org.paramedic.homeless.currenciestest.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.paramedic.homeless.currenciestest.R;
import org.paramedic.homeless.currenciestest.ui.fragment.AllRatesFragment;
import org.paramedic.homeless.currenciestest.ui.fragment.ConverterFragment;


/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
 public class MainActivityPagerAdapter extends FragmentPagerAdapter {
    private final Context context;

    public MainActivityPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return AllRatesFragment.newInstance();
            case 1: return ConverterFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.header_all_rates);
            case 1:
                return context.getString(R.string.header_converter);

        }
        return null;
    }


}