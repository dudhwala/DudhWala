package com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.model.customer.ICustomerInfoDataSource;
import com.diary.android.dudhwala.viewmodel.livedatamanager.ICustomerListLiveDataManager;

import java.util.List;

public class CustomerListLiveDataManagerImpl implements ICustomerListLiveDataManager {

    private LiveData<List<CustomerInfo>> mCustomerListLiveData;

    private ICustomerInfoDataSource customerInfoDataSource;

    public CustomerListLiveDataManagerImpl(IRepositoryFactory repositoryFactory) {

        customerInfoDataSource = repositoryFactory.getCustomerInfoRepository();
        mCustomerListLiveData = customerInfoDataSource.getAllCustomersList();
    }

    @Override
    public LiveData<List<CustomerInfo>> provideCustomerListLiveData() {
        return mCustomerListLiveData;
    }
}
