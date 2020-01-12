package com.diary.android.dudhwala.common.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "milk_transaction_table")
public class MilkTransaction {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    //same as customer_table => _id column
    @ColumnInfo(name = "customer_id")
    private int customerId;

    @ColumnInfo(name = "milk_quantity_liters")
    private float milkQuantityLiters;

    //Cow = 1
    //Buffalo = 2
    //Mix = 3
    @ColumnInfo(name = "milk_type")
    private int milkType;

    @ColumnInfo(name = "price_per_liter")
    private float pricePerLiter;

    @ColumnInfo(name = "transaction_amount")
    private float transactionAmount;

    @ColumnInfo(name = "transaction_date")
    private long transactionDate;

    @ColumnInfo(name = "created_time_stamp")
    private long createdTimeStamp;

    public MilkTransaction(int customerId, float milkQuantityLiters, int milkType,
                           float pricePerLiter, float transactionAmount,
                           long transactionDate, long createdTimeStamp) {
        this.customerId = customerId;
        this.milkQuantityLiters = milkQuantityLiters;
        this.milkType = milkType;
        this.pricePerLiter = pricePerLiter;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.createdTimeStamp = createdTimeStamp;
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

    public float getMilkQuantityLiters() {
        return milkQuantityLiters;
    }

    public int getMilkType() {
        return milkType;
    }

    public void setMilkQuantityLiters(float milkQuantityLiters) {
        this.milkQuantityLiters = milkQuantityLiters;
    }

    public float getPricePerLiter() {
        return pricePerLiter;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setPricePerLiter(float pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public void setMilkType(int milkType) {
        this.milkType = milkType;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(long createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    @NonNull
    @Override
    public String toString() {
        return "MilkTransaction{" +
                "id : " + id +
                ", customerId : " + customerId +
                ", milkQuantityLiters : " + milkQuantityLiters +
                ", milkType : " + milkType +
                ", pricePerLiter : " + pricePerLiter +
                ", transactionAmount : " + transactionAmount +
                ", transactionDate: " + transactionDate +
                ", createdTimeStamp : " + createdTimeStamp +
                "}";
    }
}