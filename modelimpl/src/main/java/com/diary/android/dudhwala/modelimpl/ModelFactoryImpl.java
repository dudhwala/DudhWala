package com.diary.android.dudhwala.modelimpl;

import android.app.Application;
import android.content.Context;

import com.diary.android.dudhwala.model.ModelFactory;
import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;

public class ModelFactoryImpl implements ModelFactory {

    private Context mContext;

    private int connectedViewModels = ViewModelType.noVM;

    public ModelFactoryImpl(Application application) {
        mContext = application;
    }


    @Override
    public boolean connected(int viewmodelType) { //TODO to monitor all connected view models
        connectedViewModels |= viewmodelType;
        return false;
    }

    @Override
    public boolean disconnected(int viewmodelType) { // TODO check if all viewmodels removed if so then tear down
        connectedViewModels &= ~viewmodelType;
        if(connectedViewModels == ViewModelType.noVM){
            // all view models cleared
        }
        return false;
    }


    @Override
    public CustomerInfoDataSource getCustomerInfoRepository(Application application) {

        return null;
    }
}
