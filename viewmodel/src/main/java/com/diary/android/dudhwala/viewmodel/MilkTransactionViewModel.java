package com.diary.android.dudhwala.viewmodel;

public interface MilkTransactionViewModel extends
        BaseViewModel,
        ViewActionListener.MilkTransactionViewActionListener,
        LiveDataSource.MilkTransactionLiveDataSource {

    void setCustomerId(int customerId);

    void setTransactionId(int unknownTransactionId);
}
