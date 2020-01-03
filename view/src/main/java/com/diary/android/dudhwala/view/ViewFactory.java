package com.diary.android.dudhwala.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.diary.android.dudhwala.view.transaction.MilkTransactionDetailDialogViewImpl;
import com.diary.android.dudhwala.view.transaction.MilkTransactionDurationViewImpl;
import com.diary.android.dudhwala.view.transaction.MilkTransactionListVIewImpl;
import com.diary.android.dudhwala.view.transaction.MilkTransactionSummeryViewImpl;

public class ViewFactory {

    private static ViewFactory INSTANCE;

    public static ViewFactory getViewFactoryInstance() {
        if (INSTANCE == null) {
            synchronized (ViewFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewFactory();
                }
            }
        }
        return INSTANCE;
    }

    public AddEditCustomerViewImpl provideAddEditCustomerView(@NonNull View view, @NonNull Context context, @NonNull LifecycleOwner lifecycleOwner) {
        return new AddEditCustomerViewImpl(view, context, lifecycleOwner);
    }

    public MilkTransactionListVIewImpl provideMilkTransactionListView(@NonNull Context context,
                                                                      @NonNull LifecycleOwner lifecycleOwner, View view) {
        return new MilkTransactionListVIewImpl(context, lifecycleOwner, view);
    }

    public MilkTransactionSummeryViewImpl provideMilkTransactionSummeryView(@NonNull Context context, @NonNull LifecycleOwner lifecycleOwner, View view) {
        return new MilkTransactionSummeryViewImpl(context, lifecycleOwner, view);
    }

    public MilkTransactionDetailDialogViewImpl provideMilkTransactionDetailView(@NonNull Context context, @NonNull LifecycleOwner lifecycleOwner) {
        return new MilkTransactionDetailDialogViewImpl(context, lifecycleOwner);
    }

    public MilkTransactionDurationViewImpl provideMilkTransactionDurationView(@NonNull Context context, @NonNull LifecycleOwner lifecycleOwner, View view) {
        return new MilkTransactionDurationViewImpl(context, lifecycleOwner, view);
    }

}
