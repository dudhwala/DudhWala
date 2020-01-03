package com.diary.android.dudhwala.viewmodel.executor;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;
import com.diary.android.dudhwala.viewmodel.executorlifecycle.LiveDataManagerLifeCycle;

public interface AddEditCustomerLiveDataManager extends LiveDataManagerLifeCycle {

    LiveData<CustomerInfo> getCustomerInfoLiveData();

    void executeUpdateCustomerData(CustomerData customerData);
}
