package com.diary.android.dudhwala.viewmodel.data;

import android.text.Editable;

public class CustomerData {

    private String mName;
    private String mNumber;
    private String mEmail;
    private String mAddress;
    private int mMilkType;
    private int mRate;

    public CustomerData(String mName, String mNumber, String mEmail, String mAddress, int mMilkType, int mRate) {
        this.mName = mName;
        this.mNumber = mNumber;
        this.mEmail = mEmail;
        this.mAddress = mAddress;
        this.mMilkType = mMilkType;
        this.mRate = mRate;
    }

    public String getName() {
        return mName;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getAddress() {
        return mAddress;
    }

    public int getMilkType() {
        return mMilkType;
    }

    public int getRate() {
        return mRate;
    }
}
