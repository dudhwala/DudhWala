package com.diary.android.dudhwala.model;

import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;

public interface RepositoryFactory extends RepositoryFactoryLifecycle {

    CustomerInfoDataSource getCustomerInfoRepository();

    MilkTransactionDataSource getMilkTransactionRepository();

}
