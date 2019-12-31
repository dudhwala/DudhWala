package com.diary.android.dudhwala.modelimpl.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_table")
public class Transaction {

    @ColumnInfo(name = "transaction_amount")
    private int transactionAmount;

    public Transaction(int customerId, int quantity, int milkType,
                       int pricePerLiter, int transactionAmount,
                       long transactionDate, boolean isPaid) {
        this.customerId = customerId;
        this.quantity = quantity;
        this.milkType = milkType;
        this.pricePerLiter = pricePerLiter;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.isPaid = isPaid;
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

    public int getQuantity() {
        return quantity;
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

    public boolean isPaid() {
        return isPaid;
    }

    @ColumnInfo(name = "transaction_date")
    private long transactionDate;

    @ColumnInfo(name = "is_paid")
    private boolean isPaid;

}