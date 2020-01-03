package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.view.LiveDataObserver.MillTransactionLiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;

public class MilkTransactionSummeryViewImpl implements
        MillTransactionLiveDataObserver {

    private static final String TAG = "DudhWala/MilkTransactionSummeryViewImpl";
    private final LifecycleOwner mLifecycleOwner;
    private final Context mContext;
    private TextView mSummeryQuantityTextView;
    private TextView mSummeryAmountTextView;

    public MilkTransactionSummeryViewImpl(Context context, LifecycleOwner lifecycleOwner, View view) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mSummeryQuantityTextView = view.findViewById(R.id.summeryQuantityTextView);
        mSummeryAmountTextView = view.findViewById(R.id.mSummeryAmountTextView);
    }

    @Override
    public void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                       MilkTransactionViewActionListener viewActionListener) {
        Log.d(TAG, "startObservingLiveData()");
        liveDataSource.provideMilkTransactionSummeryLiveData().ifPresent(this::setObserver);
    }

    private void setObserver(LiveData<SummeryData> milkTransactionLiveData) {
        milkTransactionLiveData.observe(mLifecycleOwner, this::updateSummeryView);
    }

    private void updateSummeryView(SummeryData summeryData) {

        mSummeryQuantityTextView.setText(summeryData.getTotalMilkQuantityInLitersForDuration() + " Litters");
        mSummeryAmountTextView.setText(String.valueOf(summeryData.getTotalAmountForDuration()));
    }
}
