package com.diary.android.dudhwala.view;

import com.diary.android.dudhwala.viewmodel.LiveDataSource.AddEditLiveDataSource;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionDialogLiveDataSource;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionDurationLiveDataSource;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionSummeryLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionDetailDialogViewActionListener;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionDurationViewActionListener;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;

public interface LiveDataObserver {

    interface AddEditLiveDataObserver {
        void startObservingLiveData(AddEditLiveDataSource addEditLiveDataSource,
                                    ViewActionListener.AddEditViewActionListner addEditViewActionListener);
    }

    interface TransactionListLiveDataObserver {
        void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                    MilkTransactionViewActionListener viewActionListener);

    }

    interface TransactionSummeryLiveDataObserver {
        void startObservingLiveData(MilkTransactionSummeryLiveDataSource
                                            liveDataSource);

    }

    interface TransactionDurationLiveDataObserver {
        void startObservingLiveData(MilkTransactionDurationLiveDataSource liveDataSource,
                                    MilkTransactionDurationViewActionListener viewActionListener);

    }

    interface TransactionDetailDialogLiveDataObserver {
        void startObservingLiveData(MilkTransactionDialogLiveDataSource liveDataSource,
                                    MilkTransactionDetailDialogViewActionListener viewActionListener);

    }

}
