package com.diary.android.dudhwala.modelimpl;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;

import com.diary.android.dudhwala.common.CommonThreadPool;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.MilkTransactionDataSource;
import com.diary.android.dudhwala.modelimpl.database.DudhwalaDatabase;

import java.util.List;

public class MilkTransactionRepository implements MilkTransactionDataSource {

    private static final String TAG = "DudhWala/MilkTransactionRepository";
    DudhwalaDatabase mDb;

    MilkTransactionRepository(Application application) {
        mDb = DudhwalaDatabase.getDatabase(application);
    }

    @Override
    public LiveData<List<MilkTransaction>> getMilkTransactions(int customerId,
                                                               long fromTimestamp, long toTimestamp) {
        Log.d(TAG, "getMilkTransactions() fromTimestamp : " + fromTimestamp
                + ", toTimestamp : " + toTimestamp);
        return mDb.milkTransactionDao().getMilkTransactionsForDuration(customerId,
                fromTimestamp, toTimestamp);
    }

    @Override
    public void updateMilkTransaction(MilkTransaction milkTransaction) {
        CommonThreadPool.getThreadPool().execute(() -> mDb.milkTransactionDao()
                .updateTransaction(milkTransaction));
    }

    @Insert
    public void insertMilkTransaction(MilkTransaction milkTransaction) {
        CommonThreadPool.getThreadPool().execute(() -> mDb.milkTransactionDao()
                .insertMilkTransaction(milkTransaction));

    }
}
