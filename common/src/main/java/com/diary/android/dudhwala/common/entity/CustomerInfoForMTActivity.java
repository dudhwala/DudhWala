package com.diary.android.dudhwala.common.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class CustomerInfoForMTActivity {

    @ColumnInfo(name = "customer_name")
    private String customerName;

    @ColumnInfo(name = "mobile_number")
    private String mobileNumber;

    @ColumnInfo(name = "total_amount_due")
    private int totalAmountDue;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(int totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }
}
