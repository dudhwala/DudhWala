package com.diary.android.dudhwala.viewmodel.data;

public class SummeryData {

    private int mTotalMilkQuantityInLitersForDuration;
    private int mTotalAmountForDuration;
    private int mTotalAmountDue;

    public SummeryData() {
    }

    public SummeryData(int totalMilkQuantityInLitersForDuration,
                       int totalAmountForDuration, int totalAmountDue) {
        this.mTotalMilkQuantityInLitersForDuration = totalMilkQuantityInLitersForDuration;
        this.mTotalAmountForDuration = totalAmountForDuration;
        this.mTotalAmountDue = totalAmountDue;
    }

    public int getTotalMilkQuantityInLitersForDuration() {
        return mTotalMilkQuantityInLitersForDuration;
    }

    public void setTotalMilkQuantityInLitersForDuration(int totalMilkQuantityInLitersForDuration) {
        this.mTotalMilkQuantityInLitersForDuration = totalMilkQuantityInLitersForDuration;
    }

    public int getTotalAmountForDuration() {
        return mTotalAmountForDuration;
    }

    public void setTotalAmountForDuration(int totalAmountForDuration) {
        this.mTotalAmountForDuration = totalAmountForDuration;
    }

    public int getTotalAmountDue() {
        return mTotalAmountDue;
    }

    public void setTotalAmountDue(int totalAmountDue) {
        this.mTotalAmountDue = totalAmountDue;
    }
}
