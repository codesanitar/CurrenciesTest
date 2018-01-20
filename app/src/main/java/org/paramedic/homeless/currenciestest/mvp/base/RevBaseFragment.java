package org.paramedic.homeless.currenciestest.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.paramedic.homeless.currenciestest.R;

public abstract class RevBaseFragment<V extends BaseView, P extends BaseFragmentPresenter<V>> extends BaseFragment<P,V> {

    protected final String TAG = this.getClass().getSimpleName();

    private View layoutEmpty;
    private boolean emptyAnimationAllowed = false;

    abstract public P getPresenter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutEmpty = view.findViewById(R.id.layout_empty);
    }

    public void hideEmptyAnimation() {
        if (emptyAnimationAllowed) {
            layoutEmpty.setVisibility(View.GONE);
            emptyAnimationAllowed = false;
        }
    }

    public void showEmptyAnimation() {
        if (!emptyAnimationAllowed) {
            layoutEmpty.setVisibility(View.VISIBLE);
            emptyAnimationAllowed = true;
        }
    }

}
