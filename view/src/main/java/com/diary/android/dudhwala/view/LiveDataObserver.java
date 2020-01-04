package com.diary.android.dudhwala.view;

import com.diary.android.dudhwala.viewmodel.LiveDataSource.*;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.*;

public interface LiveDataObserver {

    interface CustomerListLiveDataObserver {
        void startObservingLiveData(CustomerListLiveDataSource liveDataSource,
                                    CustomerListViewActionListener viewActionListener);
    }

    interface AddEditLiveDataObserver {
        void startObservingLiveData(AddEditLiveDataSource liveDataSource,
                                    AddEditViewActionListner viewActionListner);
    }

    interface MillTransactionLiveDataObserver {
        void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                    MilkTransactionViewActionListener viewActionListener);

    }
}
