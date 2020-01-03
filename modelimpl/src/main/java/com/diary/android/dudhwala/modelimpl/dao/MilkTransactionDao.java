package com.diary.android.dudhwala.modelimpl.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.diary.android.dudhwala.common.entity.MilkTransaction;

import java.util.List;

@Dao
public interface MilkTransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMilkTransaction(MilkTransaction transaction);

    @Query("SELECT * FROM milk_transaction_table")
    LiveData<List<MilkTransaction>> getAllMilkTransactions();

    @Query("SELECT * FROM milk_transaction_table where customer_id = :customerId " +
            "AND transaction_date > :fromTimestamp AND transaction_date < :toTimestamp")
    LiveData<List<MilkTransaction>> getMilkTransactionsForDuration(int customerId, long fromTimestamp, long toTimestamp);

    @Update
    void updateTransaction(MilkTransaction milkTransaction);

    @Query("DELETE FROM milk_transaction_table")
    void deleteAllMilkTransaction();
}
