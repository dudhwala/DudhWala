package com.diary.android.dudhwala.modelimpl.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.diary.android.dudhwala.common.entity.MilkTransaction;

import java.util.List;

@Dao
public interface MilkTransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMilkTransaction(MilkTransaction transaction);

    @Query("SELECT * FROM milk_transaction_table")
    LiveData<List<MilkTransaction>> getAllMilkTransactions();

    @Query("SELECT * FROM milk_transaction_table where customer_id = :customerId " +
            "AND transaction_date >= :fromTimestamp AND transaction_date <= :toTimestamp" +
            " ORDER BY transaction_date ASC")
    LiveData<List<MilkTransaction>> getMilkTransactionsForDuration(
            int customerId, long fromTimestamp, long toTimestamp);

    @Query("SELECT * FROM milk_transaction_table where _id = :milkTransactionId")
    LiveData<MilkTransaction> getAllMilkTransactionsForId(int milkTransactionId);

    @Update
    void updateTransaction(MilkTransaction milkTransaction);

    @Query("DELETE FROM milk_transaction_table")
    void deleteAllMilkTransaction();

    @Delete
    void deleteMilkTransaction(MilkTransaction milkTransaction);
}
