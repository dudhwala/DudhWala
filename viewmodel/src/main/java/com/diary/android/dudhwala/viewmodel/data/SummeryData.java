package com.diary.android.dudhwala.viewmodel.data;

public class SummeryData {

    private float mTotalMilkQuantityInLitersForDuration;
    private float mTotalAmountForDuration;
    private int mTotalAmountDue;

    public SummeryData() {
    }

    public SummeryData(int totalMilkQuantityInLitersForDuration,
                       int totalAmountForDuration, int totalAmountDue) {
        this.mTotalMilkQuantityInLitersForDuration = totalMilkQuantityInLitersForDuration;
        this.mTotalAmountForDuration = totalAmountForDuration;
        this.mTotalAmountDue = totalAmountDue;
    }

    public float getTotalMilkQuantityInLitersForDuration() {
        return mTotalMilkQuantityInLitersForDuration;
    }

    public void setTotalMilkQuantityInLitersForDuration(float totalMilkQuantityInLitersForDuration) {
        this.mTotalMilkQuantityInLitersForDuration = totalMilkQuantityInLitersForDuration;
    }

    public float getTotalAmountForDuration() {
        return mTotalAmountForDuration;
    }

    public void setTotalAmountForDuration(float totalAmountForDuration) {
        this.mTotalAmountForDuration = totalAmountForDuration;
    }

    public int getTotalAmountDue() {
        return mTotalAmountDue;
    }

    public void setTotalAmountDue(int totalAmountDue) {
        this.mTotalAmountDue = totalAmountDue;
    }
}
