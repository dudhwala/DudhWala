package com.diary.android.dudhwala.viewmodel;

public interface MilkTransactionViewModel extends
        BaseViewModel,
        ViewActionListener.MilkTransactionViewActionListener,
        ViewActionListener.MilkTransactionDurationViewActionListener,
        ViewActionListener.MilkTransactionDetailDialogViewActionListener,
        LiveDataSource.MilkTransactionLiveDataSource,
        LiveDataSource.MilkTransactionDialogLiveDataSource,
        LiveDataSource.MilkTransactionSummeryLiveDataSource,
        LiveDataSource.MilkTransactionDurationLiveDataSource {

    void onClickAddNewTransactionFab();

    void injectLiveDataManager();

}
