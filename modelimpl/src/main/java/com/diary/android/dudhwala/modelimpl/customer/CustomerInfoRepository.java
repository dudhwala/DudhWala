package com.diary.android.dudhwala.modelimpl.customer;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;
import com.diary.android.dudhwala.modelimpl.database.DudhwalaDatabase;

public class CustomerInfoRepository implements CustomerInfoDataSource {

    private final DudhwalaDatabase db;

    public CustomerInfoRepository(Context context) {

        db = DudhwalaDatabase.getDatabase(context);

    }

    @Override
    public int addCustomerInfo(CustomerInfo customerInfo) {

         db.customerInfoDao().insertCustomerInfo(customerInfo);
         return 909090;  // TOCHECK shouldn't db query return int ?
    }

    @Override
    public int editCustomerInfo(CustomerInfo customerInfo) {

        db.customerInfoDao().insertCustomerInfo(customerInfo);
        return 909090;  // TOCHECK shouldn't db query return int ?
    }

    @Override
    public LiveData<CustomerInfo> getCustomerInfo(int customerId) {
        return db.customerInfoDao().getCustomerInfoByCustomerId(customerId);
    }
}
