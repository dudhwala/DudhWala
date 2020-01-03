package com.diary.android.dudhwala.view.transaction;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.LiveDataObserver.MillTransactionLiveDataObserver;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;

public class MilkTransactionDetailDialogViewImpl
        implements MillTransactionLiveDataObserver {

    private final Context mContext;
    private final LifecycleOwner mLifecycleOwner;
    private MilkTransactionViewActionListener mViewActionListener;

    public MilkTransactionDetailDialogViewImpl(Context context, LifecycleOwner lifecycleOwner) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                       MilkTransactionViewActionListener viewActionListener) {
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
