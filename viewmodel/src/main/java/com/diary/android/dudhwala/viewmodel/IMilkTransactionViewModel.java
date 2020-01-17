package com.diary.android.dudhwala.viewmodel;

public interface IMilkTransactionViewModel extends
        BaseViewModel,
        IViewActionListener.MilkTransactionViewActionListener,
        IViewActionListener.ICustomCalendarViewActionListener,
        ILiveDataSource.MilkTransactionLiveDataSource,
        ILiveDataSource.ICustomCalendarLiveDataSource {

    void setCustomerId(int customerId);
}
