package com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.RepositoryFactory;
import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;
import com.diary.android.dudhwala.viewmodel.executor.CustomerListLiveDataManager;

import java.util.List;

public class CustomerListLiveDataManagerImpl implements CustomerListLiveDataManager {

    private LiveData<List<CustomerInfo>> mCustomerListLiveData;

    private CustomerInfoDataSource customerInfoDataSource;

    public CustomerListLiveDataManagerImpl(RepositoryFactory repositoryFactory) {

        customerInfoDataSource = repositoryFactory.getCustomerInfoRepository();
        mCustomerListLiveData = customerInfoDataSource.getAllCustomersList();
    }

    @Override
    public LiveData<List<CustomerInfo>> provideCustomerListLiveData() {
        return mCustomerListLiveData;
    }
}
