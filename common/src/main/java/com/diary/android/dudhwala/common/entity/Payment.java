package com.diary.android.dudhwala.common.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "payments_table")
public class Payment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    //same as customer_table => _id column
    @ColumnInfo(name = "customer_id")
    private int customerId;

    @ColumnInfo(name = "payment_amount")
    private int paymentAmount;

    @ColumnInfo(name = "payment_date")
    private long paymentDate;

    public Payment(int customerId, int paymentAmount, long paymentDate) {
        this.customerId = customerId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
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

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public long getPaymentDate() {
        return paymentDate;
    }
}
