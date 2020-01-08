package com.diary.android.dudhwala.model;

import com.diary.android.dudhwala.model.customer.ICustomerInfoDataSource;

public interface IRepositoryFactory extends IRepositoryFactoryLifecycle {

    ICustomerInfoDataSource getCustomerInfoRepository();

    IMilkTransactionDataSource getMilkTransactionRepository();

}
