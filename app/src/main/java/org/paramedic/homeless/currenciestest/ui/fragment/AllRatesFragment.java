package org.paramedic.homeless.currenciestest.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.paramedic.homeless.currenciestest.R;
import org.paramedic.homeless.currenciestest.di.component.MvpComponent;
import org.paramedic.homeless.currenciestest.mvp.base.RevBaseFragment;
import org.paramedic.homeless.currenciestest.mvp.base.loader.PresenterFactory;
import org.paramedic.homeless.currenciestest.mvp.presenter.AllRatesFragmentPresenter;
import org.paramedic.homeless.currenciestest.mvp.view.AllRatesFragmentView;
import org.paramedic.homeless.currenciestest.service.data.repository.RateEntity;
import org.paramedic.homeless.currenciestest.ui.adapter.AllRatesRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

public class AllRatesFragment extends RevBaseFragment<AllRatesFragmentView, AllRatesFragmentPresenter>
        implements  AllRatesFragmentView, AllRatesRecyclerViewAdapter.OnListFragmentInteractionListener {

    @Inject
    PresenterFactory<AllRatesFragmentPresenter> mPresenterFactory;

    @Override
    public AllRatesFragmentPresenter getPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    protected PresenterFactory<AllRatesFragmentPresenter> getPresenterFactory()
    {
        return mPresenterFactory;
    }


    private AllRatesRecyclerViewAdapter adapter;
    private Parcelable layoutManagerState = null;
    private RecyclerView recyclerView;
    private LinearLayoutManager recyclerViewLayoutManager;
    public static final String LAYOUT_STATE = "LayoutState";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllRatesFragment() {
    }

    @SuppressWarnings("unused")
    public static AllRatesFragment newInstance() {
        return new AllRatesFragment();
    }


    @Override
    protected void injectDependencies(MvpComponent mvpComponent) {
        mvpComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_rates_fragment_item_list, container, false);

        if (savedInstanceState != null && savedInstanceState.containsKey(LAYOUT_STATE)) {
            layoutManagerState = savedInstanceState.getParcelable(LAYOUT_STATE);
        }

        recyclerView = view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showEmptyAnimation();
    }

    @Override
    public void refreshContent(List<RateEntity> rateEntities) {
        adapter.refreshContent(rateEntities);
        if (rateEntities.size() > 0) {
            hideEmptyAnimation();
        }
    }

    @Override
    public void initRecycler(List<RateEntity> recentRates) {
        if (recyclerViewLayoutManager == null) {
            recyclerViewLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        }

        if (adapter == null) {
            adapter = new AllRatesRecyclerViewAdapter(recentRates, this);
            adapter.setHasStableIds(true);
        }

        recyclerView.setSaveEnabled(true);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(adapter);

        if (layoutManagerState != null) {
            recyclerViewLayoutManager.onRestoreInstanceState(layoutManagerState);
        }

        if (recentRates.size() > 0) {
            hideEmptyAnimation();
        }
    }

    @Override
    public void onClickItem(int position) {
        if (adapter != null && ! recyclerView.isAnimating()) {
            final int itemId = (int)adapter.getItemId(position);
            if (itemId > 0) {
                getPresenter().swapBaseRate(itemId)
                        .subscribe(aBoolean -> {}
                                , Throwable::printStackTrace
                        );
            }
        }
    }

    @Override
    public void onDestroyView() {
        // for memory leak prevention (RecycleView is not unsubscibed from adapter DataObserver)
        if (recyclerView != null) {
            if (recyclerView.getLayoutManager() != null) {
                layoutManagerState = recyclerView.getLayoutManager().onSaveInstanceState();
            }
            recyclerView.setAdapter(null);
            recyclerView.setLayoutManager(null);
        }

        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (recyclerViewLayoutManager != null) {
            outState.putParcelable(LAYOUT_STATE, recyclerViewLayoutManager.onSaveInstanceState());
        }
        super.onSaveInstanceState(outState);
    }

}
