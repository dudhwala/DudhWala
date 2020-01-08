package com.diary.android.dudhwala.view;

import com.diary.android.dudhwala.viewmodel.ILiveDataSource.AddEditLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.CustomerListLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.AddEditViewActionListener;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.CustomerListViewActionListener;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionViewActionListener;

public interface LiveDataObserver {

    interface CustomerListLiveDataObserver {
        void startObservingLiveData(CustomerListLiveDataSource liveDataSource,
                                    CustomerListViewActionListener viewActionListener);
    }

    interface AddEditLiveDataObserver {
        void startObservingLiveData(AddEditLiveDataSource liveDataSource,
                                    AddEditViewActionListener viewActionListener);
    }

    interface MillTransactionLiveDataObserver {
        void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                    MilkTransactionViewActionListener viewActionListener);

    }
}
