package com.diary.android.dudhwala.viewmodelimpl.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customer_table")
public class Customer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "customer_name")
    private String customerName;

    @ColumnInfo(name = "mobile_number")
    private String mobileNumber;

    @ColumnInfo(name = "email_address")
    private String emailAddress;

    @ColumnInfo(name = "address")
    private String address;

    //Cow = 1
    //Buffalo = 2
    @ColumnInfo(name = "milk_type")
    private int milkType;

    @ColumnInfo(name = "price_per_liter_cow")
    private int pricePerLiterCow;

    @ColumnInfo(name = "price_per_liter_buffalo")
    private int pricePerLiterBuffalo;

    @ColumnInfo(name = "quick_add_quantity")
    private int quickAddQuantity;

    @ColumnInfo(name = "amount_due")
    private int amountDue;

    @ColumnInfo(name = "total_milk_quantity")
    private int totalMilkQuantity;

    //Customer = 1
    //Seller = 2
    @ColumnInfo(name = "account_type")
    private int accountType;
}
