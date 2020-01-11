package com.diary.android.dudhwala.viewmodel;

public interface IAddEditCustomerViewModel extends
        BaseViewModel,
        ILiveDataSource.AddEditLiveDataSource,
        IViewActionListener.AddEditViewActionListener {

    void setCustomerId(int customerId);
}
