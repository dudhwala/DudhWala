package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.model.ModelFactory;

public interface AddEditCustomerViewModel extends LiveDataSource.AddEditLiveDataSource, ViewActionListener.AddEditViewActionListner {

    boolean isNewInstance();

    void markThisInstance();

     void injectExecutors();

    void setCustomerId(int customerId);

    void injectModelFactory(ModelFactory modelFactory);

}
