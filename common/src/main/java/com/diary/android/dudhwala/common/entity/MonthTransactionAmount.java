package com.diary.android.dudhwala.common.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "monthly_trans_amount")
public class MonthTransactionAmount {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    //same as customer_table => _id column
    @ColumnInfo(name = "customer_id")
    private int customerId;

    @ColumnInfo(name = "month_amount")
    private int monthAmount;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "year")
    private int year;

    public MonthTransactionAmount(int customerId, int monthAmount, int month,
                                  int year) {
        this.customerId = customerId;
        this.monthAmount = monthAmount;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getMonthAmount() {
        return monthAmount;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
