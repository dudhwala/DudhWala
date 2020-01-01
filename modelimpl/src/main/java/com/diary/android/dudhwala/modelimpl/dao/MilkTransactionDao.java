package com.diary.android.dudhwala.modelimpl.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.diary.android.dudhwala.common.entity.MilkTransaction;

@Dao
public interface MilkTransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMilkTransaction(MilkTransaction transaction);

    @Query("DELETE from milk_transaction_table")
    void deleteAllMilkTransaction();
}
