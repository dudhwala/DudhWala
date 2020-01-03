package com.diary.android.dudhwala.modelimpl.customer;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.CommonThreadPool;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;
import com.diary.android.dudhwala.modelimpl.dao.CustomerInfoDao;
import com.diary.android.dudhwala.modelimpl.database.DudhwalaDatabase;

public class CustomerInfoRepository implements CustomerInfoDataSource {

    private final DudhwalaDatabase mDb;
    private final CustomerInfoDao mCustomerInfoDao;

    public CustomerInfoRepository(Context context) {

        mDb = DudhwalaDatabase.getDatabase(context);
        mCustomerInfoDao = mDb.customerInfoDao();

    }

    @Override
    public int addCustomerInfo(CustomerInfo customerInfo) {

        CommonThreadPool.getThreadPool().execute(() -> mCustomerInfoDao.insertCustomerInfo(customerInfo));
        return 909090;  // TOCHECK shouldn't db query return int ?
    }

    @Override
    public int editCustomerInfo(CustomerInfo customerInfo) {

        CommonThreadPool.getThreadPool().execute(() -> mCustomerInfoDao.insertCustomerInfo(customerInfo));
        return 909090;  // TOCHECK shouldn't db query return int ?
    }

    @Override
    public LiveData<CustomerInfo> getCustomerInfo(int customerId) {
        return mDb.customerInfoDao().getCustomerInfoByCustomerId(customerId);
    }
}
