package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.LiveDataObserver;
import com.diary.android.dudhwala.viewmodel.LiveDataSource;

public class MilkTransactionSummeryViewImpl implements
        LiveDataObserver.TransactionSummeryLiveDataObserver {

    private static final String TAG = "DudhWala/MilkTransactionSummeryViewImpl";
    private final LifecycleOwner mLifecycleOwner;
    private final Context mContext;

    public MilkTransactionSummeryViewImpl(Context context, LifecycleOwner lifecycleOwner) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public void startObservingLiveData(LiveDataSource.MilkTransactionSummeryLiveDataSource
                                               liveDataSource) {
        Log.d(TAG, "startObservingLiveData()");
        liveDataSource.provideMilkTransactionSummeryLiveData().ifPresent(this::observeSummeryData);
    }

    private void observeSummeryData(LiveData<MilkTransaction> milkTransactionLiveData) {
        milkTransactionLiveData.observe(mLifecycleOwner,
                milkTransaction -> {
                    Log.d(TAG, "onChangeTransactionSummery()");

                });
    }
}
