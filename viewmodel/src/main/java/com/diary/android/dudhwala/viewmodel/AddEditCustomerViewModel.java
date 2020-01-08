package com.diary.android.dudhwala.viewmodel;

public interface AddEditCustomerViewModel extends
        BaseViewModel,
        ILiveDataSource.AddEditLiveDataSource,
        IViewActionListener.AddEditViewActionListener {

    void setCustomerId(int customerId);
}
