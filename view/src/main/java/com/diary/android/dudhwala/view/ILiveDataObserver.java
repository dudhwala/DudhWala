package com.diary.android.dudhwala.view;

import com.diary.android.dudhwala.viewmodel.ILiveDataSource.AddEditLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.CustomerListLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.ICustomCalendarLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.MilkTransactionDFLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.AddEditViewActionListener;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.CustomerListViewActionListener;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.ICustomCalendarViewActionListener;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionDFViewActionListener;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionViewActionListener;

public interface ILiveDataObserver {

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

    interface ICustomCalendarLiveDataObserver {
        void startObservingLiveData(ICustomCalendarLiveDataSource liveDataSource,
                                    ICustomCalendarViewActionListener customCalendarViewActionListener);
    }

    interface MillTransactionDFLiveDataObserver {
        void startObservingLiveData(MilkTransactionDFLiveDataSource liveDataSource,
                                    MilkTransactionDFViewActionListener viewActionListener);

    }
}
