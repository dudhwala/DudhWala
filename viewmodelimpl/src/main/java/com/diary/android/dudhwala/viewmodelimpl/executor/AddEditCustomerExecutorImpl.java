package com.diary.android.dudhwala.viewmodelimpl.executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.ModelFactory;
import com.diary.android.dudhwala.viewmodel.executor.AddEditCustomerExecutor;

public class AddEditCustomerExecutorImpl implements AddEditCustomerExecutor {

    private ModelFactory mModelFactory;

    private MutableLiveData<CustomerInfo> mCustomerInfo;

    public AddEditCustomerExecutorImpl(ModelFactory mModelFactory) {
        this.mModelFactory = mModelFactory;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

    @Override
    public LiveData<CustomerInfo> getCustomerInfoLiveData() {
        return mCustomerInfo;
    }
}
