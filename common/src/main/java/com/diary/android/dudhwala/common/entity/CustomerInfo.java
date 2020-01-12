package com.diary.android.dudhwala.common.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "customer_info_table")
public class CustomerInfo {

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

    @ColumnInfo(name = "price_per_liter_cow")
    private float pricePerLiterCow;

    @ColumnInfo(name = "price_per_liter_buffalo")
    private float pricePerLiterBuffalo;

    @ColumnInfo(name = "price_per_liter_mix")
    private float pricePerLiterMix;

    //Cow = 1
    //Buffalo = 2
    //Mix = 3
    @ColumnInfo(name = "quick_add_milk_type")
    private int quickAddMilkType;

    @ColumnInfo(name = "quick_add_quantity")
    private float quickAddQuantity;

    @ColumnInfo(name = "total_amount_due")
    private float totalAmountDue;

    @ColumnInfo(name = "last_updated_timestamp")
    private long lastUpdatedTimestamp;

    public CustomerInfo(String customerName,
                        String mobileNumber,
                        String emailAddress,
                        String address,
                        float pricePerLiterCow,
                        float pricePerLiterBuffalo,
                        float pricePerLiterMix,
                        int quickAddMilkType,
                        float quickAddQuantity,
                        float totalAmountDue,
                        long lastUpdatedTimestamp) {
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.pricePerLiterCow = pricePerLiterCow;
        this.pricePerLiterBuffalo = pricePerLiterBuffalo;
        this.pricePerLiterMix = pricePerLiterMix;
        this.quickAddMilkType = quickAddMilkType;
        this.quickAddQuantity = quickAddQuantity;
        this.totalAmountDue = totalAmountDue;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
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

    public float getPricePerLiterCow() {
        return pricePerLiterCow;
    }

    public float getPricePerLiterBuffalo() {
        return pricePerLiterBuffalo;
    }

    public float getPricePerLiterMix() {
        return pricePerLiterMix;
    }

    public int getQuickAddMilkType() {
        return quickAddMilkType;
    }

    public float getQuickAddQuantity() {
        return quickAddQuantity;
    }

    public float getTotalAmountDue() {
        return totalAmountDue;
    }

    public long getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerInfo that = (CustomerInfo) o;
        return id == that.id &&
                pricePerLiterCow == that.pricePerLiterCow &&
                pricePerLiterBuffalo == that.pricePerLiterBuffalo &&
                pricePerLiterMix == that.pricePerLiterMix &&
                quickAddMilkType == that.quickAddMilkType &&
                quickAddQuantity == that.quickAddQuantity &&
                totalAmountDue == that.totalAmountDue &&
                lastUpdatedTimestamp == that.lastUpdatedTimestamp &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(mobileNumber, that.mobileNumber) &&
                Objects.equals(emailAddress, that.emailAddress) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, mobileNumber, emailAddress, address, pricePerLiterCow,
                pricePerLiterBuffalo, pricePerLiterMix, quickAddMilkType, quickAddQuantity,
                totalAmountDue, lastUpdatedTimestamp);
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", address='" + address + '\'' +
                ", pricePerLiterCow=" + pricePerLiterCow +
                ", pricePerLiterBuffalo=" + pricePerLiterBuffalo +
                ", pricePerLiterMix=" + pricePerLiterMix +
                ", quickAddMilkType=" + quickAddMilkType +
                ", quickAddQuantity=" + quickAddQuantity +
                ", totalAmountDue=" + totalAmountDue +
                ", lastUpdatedTimestamp=" + lastUpdatedTimestamp +
                '}';
    }
}