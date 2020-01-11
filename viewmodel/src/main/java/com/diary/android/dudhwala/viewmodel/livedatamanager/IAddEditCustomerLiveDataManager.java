package com.diary.android.dudhwala.viewmodel.livedatamanager;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.viewmodel.ILiveDataManagerLifeCycle;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;

public interface IAddEditCustomerLiveDataManager extends ILiveDataManagerLifeCycle {

    LiveData<CustomerInfo> getCustomerInfoLiveData();

    void executeUpdateCustomerData(CustomerData customerData);
}
