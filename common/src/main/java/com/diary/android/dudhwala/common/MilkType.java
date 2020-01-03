package com.diary.android.dudhwala.common;

public enum MilkType {
    COW(1),
    BUFF(2),
    MIX(3);

    int intVal;

    MilkType(int val) {
        intVal = val;
    }

    public int intValue(){
        return intVal;
    }
}