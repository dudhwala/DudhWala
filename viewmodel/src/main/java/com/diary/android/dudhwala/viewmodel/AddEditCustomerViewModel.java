package com.diary.android.dudhwala.viewmodel;

public interface AddEditCustomerViewModel extends
        BaseViewModel,
        LiveDataSource.AddEditLiveDataSource,
        ViewActionListener.AddEditViewActionListner {

    void setCustomerId(int customerId);
}
