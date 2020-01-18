package com.diary.android.dudhwala.model;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.MilkTransaction;

import java.util.List;

public interface IMilkTransactionDataSource {

    LiveData<List<MilkTransaction>> getMilkTransactions(int customerId, long fromTimestamp, long toTimestamp);

    void updateMilkTransaction(MilkTransaction milkTransaction);

    void insertMilkTransaction(MilkTransaction milkTransaction);

    void deleteMilkTransaction(MilkTransaction milkTransaction);

    LiveData<MilkTransaction> getMilkTransactionForId(int milkTransactionId);
}
