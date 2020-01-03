package com.diary.android.dudhwala.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public class ViewFactory {

    public AddEditCustomerViewImpl provideAddEditCustomerView(@NonNull View view, @NonNull Context context, @NonNull LifecycleOwner lifecycleOwner) {
        return new AddEditCustomerViewImpl(view, context, lifecycleOwner);
    }

}
