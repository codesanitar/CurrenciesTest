package org.paramedic.homeless.currenciestest.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.paramedic.homeless.currenciestest.R;
import org.paramedic.homeless.currenciestest.service.data.repository.RateEntity;

import java.util.List;

import static org.paramedic.homeless.currenciestest.StaticConfig.DEFAULT_ROUNDING_MODE;

public class AllRatesRecyclerViewAdapter extends RecyclerView.Adapter<AllRatesRecyclerViewAdapter.ViewHolder> {

    private final List<RateEntity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public AllRatesRecyclerViewAdapter(List<RateEntity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_rates_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mDescription.setText(mValues.get(position).getDescription());
        holder.mImage.setImageResource(mValues.get(position).getImageId());

        holder.mContentView.setText(mValues.get(position).getValue().setScale(5, DEFAULT_ROUNDING_MODE).toPlainString());

        holder.mView.setOnClickListener(view -> {
            if (null != mListener) {
                mListener.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public long getItemId(int position) {
        return isDataValid()?mValues.get(position).getId():-1;
    }

    private boolean isDataValid() {
        return mValues != null;
    }

    public void refreshContent(List<RateEntity> rateEntities) {
        mValues.clear();
        mValues.addAll(rateEntities);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mNameView;
        final ImageView mImage;
        final TextView mDescription;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mDescription = (TextView) view.findViewById(R.id.description);
            mImage = (ImageView) view.findViewById(R.id.image);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public interface OnListFragmentInteractionListener {
        void onClickItem(int position);
    }
}
