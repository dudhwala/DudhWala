package com.diary.android.dudhwala.viewmodel.executor;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.viewmodel.executorlifecycle.LiveDataManagerLifeCycle;

import java.util.List;

public interface MilkTransactionLiveDataManager extends LiveDataManagerLifeCycle {

    interface TransactionsListLiveDataManager {
        LiveData<List<MilkTransaction>> getTransactionsArrayListLiveData();

        void onDurationChanged(long fromTimestamp, long toTimestamp);

        void onClickDurationChange(Constants.DurationDirection direction);
    }

    interface DetailDialogLiveDataManager {
        LiveData<Boolean> getDetailDialogVisibilityControllerLiveData();

        LiveData<MilkTransaction> getMilkTransactionLiveData(int milkTransactionId);
    }

    interface SummeryLiveDataManager {
        //TODO create proper return type
        LiveData<MilkTransaction> getSummeryLiveData();
    }
}
