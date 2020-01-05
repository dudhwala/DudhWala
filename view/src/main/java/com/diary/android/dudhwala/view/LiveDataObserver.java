package com.diary.android.dudhwala.view;

import com.diary.android.dudhwala.viewmodel.LiveDataSource.AddEditLiveDataSource;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.CustomerListLiveDataSource;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.AddEditViewActionListner;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.CustomerListViewActionListener;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;

public interface LiveDataObserver {

    interface CustomerListLiveDataObserver {
        void startObservingLiveData(CustomerListLiveDataSource liveDataSource,
                                    CustomerListViewActionListener viewActionListener);
    }

    interface AddEditLiveDataObserver {
        void startObservingLiveData(AddEditLiveDataSource liveDataSource,
                                    AddEditViewActionListner viewActionListener);
    }

    interface MillTransactionLiveDataObserver {
        void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                    MilkTransactionViewActionListener viewActionListener);

    }
}
