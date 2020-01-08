package com.diary.android.dudhwala.viewmodel.livedatamanager;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.viewmodel.ILiveDataManagerLifeCycle;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;

import java.util.List;

public interface IMilkTransactionLiveDataManager extends ILiveDataManagerLifeCycle {

    interface TransactionsListLiveDataManager {
        LiveData<List<MilkTransaction>> getTransactionsArrayListLiveData();

        LiveData<MilkTransaction> getSelectedMilkTransaction();

        void updateTransactionId(int transactionId);

        void updateCurrentMilkTransaction(MilkTransaction milkTransaction);

        void updateMilkTransactionDuration(long fromTimestamp, long toTimestamp);

        void deleteMilkTransaction(MilkTransaction milkTransaction);
    }

    interface DialogLiveDataManager {
        LiveData<MilkTransaction> getMilkTransactionLiveData(int milkTransactionId);

        void insertNewMilkTransaction(MilkTransaction milkTransaction);

        void saveCurrentMilkTransactionState(MilkTransaction milkTransaction);

        void updateMilkType(int milkType, long date, int price, int quantity);
    }

    interface SummeryLiveDataManager {
        LiveData<SummeryData> getSummeryLiveData();

        LiveData<CustomerInfo> getCustomerInfoLiveData();
    }
}