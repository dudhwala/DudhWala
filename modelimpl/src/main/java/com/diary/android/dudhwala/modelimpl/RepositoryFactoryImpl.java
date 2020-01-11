package com.diary.android.dudhwala.modelimpl;

import android.app.Application;

import com.diary.android.dudhwala.model.IMilkTransactionDataSource;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.model.customer.ICustomerInfoDataSource;
import com.diary.android.dudhwala.modelimpl.customer.CustomerInfoRepository;

public class RepositoryFactoryImpl implements IRepositoryFactory {

    private Application mApplication;

    private int connectedViewModels = ViewModelType.noVM;
    private MilkTransactionRepository mMilkTransactionRepository;

    private ICustomerInfoDataSource mCustomerInfoDataSource;

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
    public ICustomerInfoDataSource getCustomerInfoRepository() {

        if (mCustomerInfoDataSource == null) {
            mCustomerInfoDataSource = new CustomerInfoRepository(mApplication);
        }
        return mCustomerInfoDataSource;
    }

    @Override
    public IMilkTransactionDataSource getMilkTransactionRepository() {
        if (mMilkTransactionRepository == null) {
            mMilkTransactionRepository = new MilkTransactionRepository(mApplication);
        }
        return mMilkTransactionRepository;
    }
}
