package com.diary.android.dudhwala.modelimpl.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.diary.android.dudhwala.common.entity.CustomerInfo;

@Dao
public interface CustomerInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCustomerInfo(CustomerInfo customerInfo); //TODO int return type krne pr "error: Not sure how to handle insert method's return type."

    @Query("DELETE from customer_info_table")
    void deleteAllCustomerInfo();

    @Query("SELECT * from customer_info_table where _id = :customerId")
    LiveData<CustomerInfo> getCustomerInfoByCustomerId(int customerId);
}
