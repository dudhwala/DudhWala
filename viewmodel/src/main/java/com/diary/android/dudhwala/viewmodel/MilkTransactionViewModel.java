package com.diary.android.dudhwala.viewmodel;

public interface MilkTransactionViewModel extends
        BaseViewModel,
        ViewActionListener.MilkTransactionViewActionListener,
        LiveDataSource.MilkTransactionLiveDataSource {

    void onClickAddNewTransactionFab();

    void setCustomerId(int customerId);

}
