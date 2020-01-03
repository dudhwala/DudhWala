package com.diary.android.dudhwala.view;

import com.diary.android.dudhwala.viewmodel.LiveDataSource.AddEditLiveDataSource;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;

public interface LiveDataObserver {

    interface AddEditLiveDataObserver {
        void startObservingLiveData(AddEditLiveDataSource addEditLiveDataSource,
                                    ViewActionListener.AddEditViewActionListner addEditViewActionListener);
    }

    interface MillTransactionLiveDataObserver {
        void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                    MilkTransactionViewActionListener viewActionListener);

    }
}
