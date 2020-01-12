package com.diary.android.dudhwala.viewmodel.data;

public class CustomerData {

    private String mName;
    private String mNumber;
    private String mEmail;
    private String mAddress;
    private int mMilkType;
    private float mRate;

    public CustomerData(String mName, String mNumber, String mEmail, String mAddress, int mMilkType, float mRate) {
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

    public float getRate() {
        return mRate;
    }
}
