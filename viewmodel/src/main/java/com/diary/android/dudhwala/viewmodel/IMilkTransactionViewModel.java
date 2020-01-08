package com.diary.android.dudhwala.viewmodel;

public interface IMilkTransactionViewModel extends
        BaseViewModel,
        IViewActionListener.MilkTransactionViewActionListener,
        ILiveDataSource.MilkTransactionLiveDataSource {

    void setCustomerId(int customerId);

    void setTransactionId(int unknownTransactionId);
}
