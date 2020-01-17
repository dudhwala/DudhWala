package com.diary.android.dudhwala.viewmodel;

public interface MilkTransactionDFViewModel extends
        BaseViewModel,
        ILiveDataSource.MilkTransactionDFLiveDataSource,
        IViewActionListener.MilkTransactionDFViewActionListender {

    void setMilkTransactionId(int id);

    void setCustomerId(int id);

}
