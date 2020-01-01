package com.diary.android.dudhwala.common.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.diary.android.dudhwala.common.entity.Payment;

@Dao
public interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPayment(Payment payment);

    @Query(value = "DELETE from payments_table")
    void deleteAllPayments();

    @Query(value = "SELECT * from payments_table")
    Cursor getAllPayments();
}
