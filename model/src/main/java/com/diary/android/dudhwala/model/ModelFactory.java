package com.diary.android.dudhwala.model;

import android.app.Application;
import android.content.Context;

import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;

public interface ModelFactory extends ModelFactoryLifecycle{

    CustomerInfoDataSource getCustomerInfoRepository(Application application);

}
