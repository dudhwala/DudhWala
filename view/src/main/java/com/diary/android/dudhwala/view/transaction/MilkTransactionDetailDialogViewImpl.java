package com.diary.android.dudhwala.view.transaction;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.LiveDataObserver;
import com.diary.android.dudhwala.viewmodel.LiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener;

public class MilkTransactionDetailDialogViewImpl
        implements LiveDataObserver.TransactionDetailDialogLiveDataObserver {

    private final Context mContext;
    private final LifecycleOwner mLifecycleOwner;
    private ViewActionListener.MilkTransactionDetailDialogViewActionListener mViewActionListener;

    public MilkTransactionDetailDialogViewImpl(Context context, LifecycleOwner lifecycleOwner) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public void startObservingLiveData(LiveDataSource.MilkTransactionDialogLiveDataSource liveDataSource,
                                       ViewActionListener.MilkTransactionDetailDialogViewActionListener viewActionListener) {
        mViewActionListener = viewActionListener;
        liveDataSource.provideDialogMilkTransactionLiveData().ifPresent(this::observeMilkTransactionData);
        liveDataSource.provideDialogVisibilityControllerLiveData().ifPresent(this::observeDialogVisibilityControllerLiveData);
    }

    private void observeDialogVisibilityControllerLiveData(LiveData<Boolean> visibilityLiveData) {
        visibilityLiveData.observe(mLifecycleOwner,
                visible -> {
                    //TODO update dialog visibility
                });
    }

    private void observeMilkTransactionData(LiveData<MilkTransaction> milkTransactionLiveData) {
        milkTransactionLiveData.observe(mLifecycleOwner,
                milkTransaction -> {
                    //TODO
                });
    }
}
