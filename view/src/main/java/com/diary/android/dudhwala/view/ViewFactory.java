package com.diary.android.dudhwala.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public class ViewFactory {

    public AddEditCustomerViewImpl provideAddEditCustomerView(@NonNull Context context, @NonNull LifecycleOwner lifecycleOwner){
        return new AddEditCustomerViewImpl(context, lifecycleOwner);
    }

}
