package com.diary.android.dudhwala.modelimpl.customer;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.CommonThreadPool;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.customer.ICustomerInfoDataSource;
import com.diary.android.dudhwala.modelimpl.dao.CustomerInfoDao;
import com.diary.android.dudhwala.modelimpl.database.DudhwalaDatabase;

import java.util.List;

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

public class CustomerInfoRepository implements ICustomerInfoDataSource {

    private final String TAG = _TAG + "CustomerInfoRepository";

    private final DudhwalaDatabase mDb;
    private final CustomerInfoDao mCustomerInfoDao;

    public CustomerInfoRepository(Context context) {

        mDb = DudhwalaDatabase.getDatabase(context);
        mCustomerInfoDao = mDb.customerInfoDao();

    }

    @Override
    public int addCustomerInfo(CustomerInfo customerInfo) {
        Log.d(TAG, "addCustomerInfo() customerInfo : " + customerInfo);

        CommonThreadPool.getThreadPool().execute(() -> {
            try {
                mCustomerInfoDao.insertCustomerInfo(customerInfo);
            } catch (Exception e) {
                Log.d(TAG, "Exception " + e);
            }
        });

        return 909090;  // TOCHECK shouldn't db query return int ?
    }

    @Override
    public int editCustomerInfo(CustomerInfo customerInfo) {
        Log.d(TAG, "editCustomerInfo " + customerInfo);

        CommonThreadPool.getThreadPool().execute(() -> mCustomerInfoDao.insertCustomerInfo(customerInfo));

        return 909090;  // TOCHECK shouldn't db query return int ?
    }

    @Override
    public LiveData<CustomerInfo> getCustomerInfo(int customerId) {

        Log.d(TAG, "getCustomerInfo() customerId : " + customerId);
        return mDb.customerInfoDao().getCustomerInfoByCustomerId(customerId);
    }

    @Override
    public LiveData<List<CustomerInfo>> getAllCustomersList() {

        Log.d(TAG, "getAllCustomersList()");
        return mDb.customerInfoDao().getAllCustomers();
    }
}
