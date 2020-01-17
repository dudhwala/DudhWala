package com.diary.android.dudhwala.view;

import com.diary.android.dudhwala.viewmodel.ILiveDataSource.*;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.*;

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
                                    MilkTransactionDFViewActionListender viewActionListener);

    }
}
