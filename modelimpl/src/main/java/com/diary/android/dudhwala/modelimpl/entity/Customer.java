package com.diary.android.dudhwala.modelimpl.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customer_table")
public class Customer {

    public Customer(String customerName, String mobileNumber, String emailAddress,
                    String address, int milkType, int pricePerLiterCow,
                    int pricePerLiterBuffalo, int quickAddQuantity, int amountDue,
                    int totalMilkQuantity, int accountType) {
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.milkType = milkType;
        this.pricePerLiterCow = pricePerLiterCow;
        this.pricePerLiterBuffalo = pricePerLiterBuffalo;
        this.quickAddQuantity = quickAddQuantity;
        this.amountDue = amountDue;
        this.totalMilkQuantity = totalMilkQuantity;
        this.accountType = accountType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public int getMilkType() {
        return milkType;
    }

    public int getPricePerLiterCow() {
        return pricePerLiterCow;
    }

    public int getPricePerLiterBuffalo() {
        return pricePerLiterBuffalo;
    }

    public int getQuickAddQuantity() {
        return quickAddQuantity;
    }

    public int getAmountDue() {
        return amountDue;
    }

    public int getTotalMilkQuantity() {
        return totalMilkQuantity;
    }

    public int getAccountType() {
        return accountType;
    }

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