package com.diary.android.dudhwala.modelimpl.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.diary.android.dudhwala.common.entity.MonthTransactionAmount;

@Dao
public interface MonthTransactionAmountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMonthTransactionAmount(MonthTransactionAmount monthTransactionAmount);

    @Query(value = "DELETE from monthly_trans_amount")
    void deleteAllMonthTransactionAmount();

}
