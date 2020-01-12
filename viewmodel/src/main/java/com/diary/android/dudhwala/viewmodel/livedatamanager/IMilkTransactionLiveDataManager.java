package com.diary.android.dudhwala.viewmodel.livedatamanager;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.viewmodel.ILiveDataManagerLifeCycle;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;

import java.util.List;

public interface IMilkTransactionLiveDataManager extends ILiveDataManagerLifeCycle {

    LiveData<List<MilkTransaction>> getTransactionsArrayListLiveData();

    LiveData<MilkTransaction> getSelectedMilkTransaction();

    LiveData<SummeryData> getSummeryLiveData();

    LiveData<CustomerInfo> getCustomerInfoLiveData();

    void updateTransactionId(int transactionId);

    void updateMilkTransactionDuration(long fromTimestamp, long toTimestamp);

    void deleteMilkTransaction(MilkTransaction milkTransaction);

    void insertNewMilkTransaction(MilkTransaction milkTransaction);

    void saveCurrentMilkTransactionState(MilkTransaction milkTransaction);

    void updateMilkType(int milkType, long date, float price, float quantity);
}
