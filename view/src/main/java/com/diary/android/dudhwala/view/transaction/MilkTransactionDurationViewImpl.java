package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.LiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.viewmodel.LiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener;

public class MilkTransactionDurationViewImpl
        implements LiveDataObserver.TransactionDurationLiveDataObserver {

    private static final String TAG = "DudhWala/MilkTransactionDurationViewImpl";
    private final LifecycleOwner mLifecycleOwner;
    private final Context mContext;
    private final View mItemView;
    private ViewActionListener.MilkTransactionDurationViewActionListener mDurationViewActionListener;

    public MilkTransactionDurationViewImpl(Context context, LifecycleOwner lifecycleOwner, View view) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mItemView = view;

        setClickListeners();
    }

    private void setClickListeners() {
        mItemView.findViewById(R.id.durationView)
                .setOnClickListener(v -> onClickChangeDuration(Constants.DurationDirection.NEXT));
    }

    public void onClickChangeDuration(Constants.DurationDirection direction) {
        Log.d(TAG, "onClickChangeDuration() direction : " + direction);
        mDurationViewActionListener.onClickChangeDuration(direction);
    }

    @Override
    public void startObservingLiveData(LiveDataSource.MilkTransactionDurationLiveDataSource liveDataSource,
                                       ViewActionListener.MilkTransactionDurationViewActionListener viewActionListener) {

        Log.d(TAG, "startObservingLiveData()");
        mDurationViewActionListener = viewActionListener;
        liveDataSource.provideMilkTransactionDurationLiveData()
                .ifPresent(this::setObserver);
    }

    private void setObserver(LiveData<MilkTransaction> milkTransactionLiveData) {
        milkTransactionLiveData.observe(mLifecycleOwner,
                milkTransaction -> {
                    Log.d(TAG, "onChangeDurationLiveData");
                });
    }
}
