package com.diary.android.dudhwala.model;

import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;

public interface ModelFactory extends ModelFactoryLifecycle {

    CustomerInfoDataSource getCustomerInfoRepository();

}
