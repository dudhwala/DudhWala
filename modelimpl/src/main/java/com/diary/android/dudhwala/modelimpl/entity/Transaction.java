package com.diary.android.dudhwala.modelimpl.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_table")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    //same as customer_table => _id column
    @ColumnInfo(name = "customer_id")
    private int customerId;

    @ColumnInfo(name = "quantity")
    private int quantity;

    //Cow = 1
    //Buffalo = 2
    @ColumnInfo(name = "milk_type")
    private int milkType;

    @ColumnInfo(name = "price_per_liter")
    private int pricePerLiter;

    @ColumnInfo(name = "transaction_amount")
    private int transactionAmont;

    @ColumnInfo(name = "transaction_date")
    private long transactionDate;

    @ColumnInfo(name = "is_paid")
    private boolean isPaid;

}
