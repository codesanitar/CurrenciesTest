package org.paramedic.homeless.currenciestest.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.paramedic.homeless.currenciestest.R;
import org.paramedic.homeless.currenciestest.service.data.repository.RateEntity;

import java.util.List;

public class ConverterRecyclerViewAdapter extends RecyclerView.Adapter<ConverterRecyclerViewAdapter.ViewHolder> {

    private final List<RateEntity> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;


    public ConverterRecyclerViewAdapter(Context context, List<RateEntity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.converter_fragment_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mNameView.setText(mValues.get(position).getCurrency().name());
        holder.mDescription.setText(mValues.get(position).getCurrency().getDescription());
        holder.mImage.setImageResource(mValues.get(position).getCurrency().getImageId());

        if (!holder.mContentView.hasFocus()) {
            holder.mContentView.setText(mValues.get(position).getAmount());
        }
        holder.mContentView.setOnFocusChangeListener((view, hasFocus) -> {
            if(view.getId() == R.id.content && !hasFocus) {
                InputMethodManager imm =  (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });
        holder.mContentView.setOnKeyListener((view1, i, keyEvent) -> {
            if(keyEvent.getAction() == KeyEvent.ACTION_UP)
            {
                if(mListener != null) {
                    mListener.onInputAmount(position, holder.mContentView.getText().toString());
                }
                return true;
            }
            return false;
        });

        holder.mView.setOnClickListener(view -> {
            if (null != mListener) {
                mListener.onClickItem(position);
                holder.mContentView.requestFocus();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public long getItemId(int position) {
        return isDataValid()?mValues.get(position).getCurrency().getId():-1;
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
        final EditText mContentView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
            mDescription = view.findViewById(R.id.description);
            mImage = view.findViewById(R.id.image);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public interface OnListFragmentInteractionListener {
        void onClickItem(int position);
        void onInputAmount(int position, String value);
    }
}
