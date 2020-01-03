package com.diary.android.dudhwala.model.customer;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;

public interface CustomerInfoDataSource {

    int addCustomerInfo(CustomerInfo customerInfo);

    int editCustomerInfo(CustomerInfo customerInfo);

    LiveData<CustomerInfo> getCustomerInfo(int customerId);

}
