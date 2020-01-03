package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.LiveDataObserver.MillTransactionLiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;

public class MilkTransactionDurationViewImpl
        implements MillTransactionLiveDataObserver {

    private static final String TAG = "DudhWala/MilkTransactionDurationViewImpl";
    private final LifecycleOwner mLifecycleOwner;
    private final Context mContext;
    private final View mItemView;
    private MilkTransactionViewActionListener mDurationViewActionListener;

    public MilkTransactionDurationViewImpl(Context context, LifecycleOwner lifecycleOwner, View view) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mItemView = view;

        setClickListeners();
    }

    private void setClickListeners() {
        //TODO change to proper logic
        mItemView.findViewById(R.id.durationViewContainer)
                .setOnClickListener(v -> onClickChangeDuration(Constants.DurationDirection.NEXT));
    }

    private void onClickChangeDuration(Constants.DurationDirection direction) {
        Log.d(TAG, "onClickChangeDuration() direction : " + direction);
        mDurationViewActionListener.onClickChangeDuration(direction);
    }

    @Override
    public void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                       MilkTransactionViewActionListener viewActionListener) {

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
