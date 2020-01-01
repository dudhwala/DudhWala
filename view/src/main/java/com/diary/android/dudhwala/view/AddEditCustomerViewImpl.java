package com.diary.android.dudhwala.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.AddEditLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.AddEditViewActionListner;

public class AddEditCustomerViewImpl implements LiveDataObserver.AddEditLiveDataObserver {

    @NonNull
    private Context mContext;
    @NonNull
    private LifecycleOwner mLifecycleOwner;

    private AddEditViewActionListner mViewActionListener;

    AddEditCustomerViewImpl(@NonNull Context context, @NonNull LifecycleOwner lifecycleOwner) {

        mContext = context;
        mLifecycleOwner = lifecycleOwner;

    }

    @Override
    public void startObservingLiveData(AddEditLiveDataSource liveDataSource, AddEditViewActionListner viewActionListener) {
        mViewActionListener = viewActionListener;
        liveDataSource.provideCustomerInfoLiveData().ifPresent(this::setCustomerInfoLiveData);

    }

    private void setCustomerInfoLiveData(LiveData<CustomerInfo> customerInfoLiveData) {
        customerInfoLiveData.observe(mLifecycleOwner,
                customerInfo -> {
                    //TODO write logicwhen customerinfo received from DB
                });
    }
}
