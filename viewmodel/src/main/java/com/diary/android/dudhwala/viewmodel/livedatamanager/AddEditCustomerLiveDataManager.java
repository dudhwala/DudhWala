package com.diary.android.dudhwala.viewmodel.livedatamanager;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.viewmodel.LiveDataManagerLifeCycle;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;

public interface AddEditCustomerLiveDataManager extends LiveDataManagerLifeCycle {

    LiveData<CustomerInfo> getCustomerInfoLiveData();

    void executeUpdateCustomerData(CustomerData customerData);
}
