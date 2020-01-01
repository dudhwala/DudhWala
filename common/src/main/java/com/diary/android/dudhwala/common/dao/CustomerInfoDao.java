package com.diary.android.dudhwala.common.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.diary.android.dudhwala.common.entity.CustomerInfo;

@Dao
public interface CustomerInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCustomerInfo(CustomerInfo customerInfo);

    @Query("DELETE from customer_info_table")
    void deleteAllCustomerInfo();
}
