package com.diary.android.dudhwala.viewmodel;

public interface MilkTransactionDFViewModel extends
        BaseViewModel,
        ILiveDataSource.MilkTransactionDFLiveDataSource,
        IViewActionListener.MilkTransactionDFViewActionListener {

    void setMilkTransactionId(int id);

    void setCustomerId(int id);

}
