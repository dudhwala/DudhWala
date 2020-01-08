package com.diary.android.dudhwala.viewmodel.livedatamanager;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;

import java.util.List;

public interface CustomerListLiveDataManager {

    LiveData<List<CustomerInfo>> provideCustomerListLiveData();

}