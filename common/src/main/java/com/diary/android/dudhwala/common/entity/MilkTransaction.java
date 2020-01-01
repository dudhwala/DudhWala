package com.diary.android.dudhwala.common.entity;

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
    private int milkQuantityLiters;

    //Cow = 1
    //Buffalo = 2
    //Mix = 3
    @ColumnInfo(name = "milk_type")
    private int milkType;

    @ColumnInfo(name = "price_per_liter")
    private int pricePerLiter;

    @ColumnInfo(name = "transaction_amount")
    private int transactionAmount;

    @ColumnInfo(name = "transaction_date")
    private long transactionDate;

    public MilkTransaction(int customerId, int milkQuantityLiters, int milkType,
                           int pricePerLiter, int transactionAmount,
                           long transactionDate) {
        this.customerId = customerId;
        this.milkQuantityLiters = milkQuantityLiters;
        this.milkType = milkType;
        this.pricePerLiter = pricePerLiter;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
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

    public int getMilkQuantityLiters() {
        return milkQuantityLiters;
    }

    public int getMilkType() {
        return milkType;
    }

    public int getPricePerLiter() {
        return pricePerLiter;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public long getTransactionDate() {
        return transactionDate;
    }
}