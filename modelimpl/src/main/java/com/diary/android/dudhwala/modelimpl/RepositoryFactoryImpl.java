package com.diary.android.dudhwala.modelimpl;

import android.app.Application;

import com.diary.android.dudhwala.model.MilkTransactionDataSource;
import com.diary.android.dudhwala.model.RepositoryFactory;
import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;
import com.diary.android.dudhwala.modelimpl.customer.CustomerInfoRepository;

public class RepositoryFactoryImpl implements RepositoryFactory {

    private Application mApplication;

    private int connectedViewModels = ViewModelType.noVM;
    private MilkTransactionRepository mMilkTransactionRepository;

    private CustomerInfoDataSource mCustomerInfoDataSource;

    public RepositoryFactoryImpl(Application application) {
        this.mApplication = application;
    }


    @Override
    public boolean connected(int viewModelType) { //TODO to monitor all connected view models
        connectedViewModels |= viewModelType;
        return false;
    }

    @Override
    public boolean disconnected(int viewModelType) { // TODO check if all viewmodels removed if so then tear down
        connectedViewModels &= ~viewModelType;
        if (connectedViewModels == ViewModelType.noVM) {
            // all view models cleared
        }
        return false;
    }


    @Override
    public CustomerInfoDataSource getCustomerInfoRepository() {

        if (mCustomerInfoDataSource == null) {
            mCustomerInfoDataSource = new CustomerInfoRepository(mApplication);
        }
        return mCustomerInfoDataSource;
    }

    @Override
    public MilkTransactionDataSource getMilkTransactionRepository() {
        if (mMilkTransactionRepository == null) {
            mMilkTransactionRepository = new MilkTransactionRepository(mApplication);
        }
        return mMilkTransactionRepository;
    }
}
