package com.diary.android.dudhwala.model;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.MilkTransaction;

import java.util.List;

public interface MilkTransactionDataSource {

    LiveData<List<MilkTransaction>> getMilkTransactions(long fromTimestamp, long toTimestamp);

    void updateMilkTransaction(MilkTransaction milkTransaction);
}
