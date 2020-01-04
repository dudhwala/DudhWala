package com.diary.android.dudhwala.viewmodel.executor;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfoForMTActivity;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.diary.android.dudhwala.viewmodel.executorlifecycle.LiveDataManagerLifeCycle;

import java.util.List;

public interface MilkTransactionLiveDataManager extends LiveDataManagerLifeCycle {

    interface TransactionsListLiveDataManager {
        LiveData<List<MilkTransaction>> getTransactionsArrayListLiveData();

        void updateMilkTransactionDuration(long fromTimestamp, long toTimestamp);
    }

    interface DetailDialogLiveDataManager {
        LiveData<Boolean> getDetailDialogVisibilityControllerLiveData();
        LiveData<MilkTransaction> getMilkTransactionLiveData(int milkTransactionId);
    }

    interface SummeryLiveDataManager {
        LiveData<SummeryData> getSummeryLiveData();

        LiveData<CustomerInfoForMTActivity> getCustomerInfoLiveData();
    }
}
