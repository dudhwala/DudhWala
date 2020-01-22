package com.diary.android.dudhwala.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.diary.android.dudhwala.view.customerlist.CustomerListViewImpl;
import com.diary.android.dudhwala.view.transaction.MilkTransactionDialogView;
import com.diary.android.dudhwala.view.transaction.MilkTransactionListVIewImpl;
import com.diary.android.dudhwala.view.transaction.MilkTransactionSummeryAndToolbarViewImpl;

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

    public AddEditCustomerViewImpl provideAddEditCustomerView(
            @NonNull View view, @NonNull Context context, @NonNull LifecycleOwner lifecycleOwner) {
        return new AddEditCustomerViewImpl(view, context, lifecycleOwner);
    }

    public CustomerListViewImpl provideCustomerListView(@NonNull Context context,
                                                        @NonNull LifecycleOwner lifecycleOwner,
                                                        @NonNull View customerListContainer) {
        return new CustomerListViewImpl(context, lifecycleOwner, customerListContainer);
    }

    public MilkTransactionListVIewImpl provideMilkTransactionListView(
            @NonNull Context context, @NonNull LifecycleOwner lifecycleOwner, View view, int customerId) {
        return new MilkTransactionListVIewImpl(context, lifecycleOwner, view, customerId);
    }

    public MilkTransactionSummeryAndToolbarViewImpl provideMilkTransactionSummeryAndToolbarView(
            @NonNull Context context, @NonNull LifecycleOwner lifecycleOwner, View summeryView, View toolbarView) {
        return new MilkTransactionSummeryAndToolbarViewImpl(
                context, lifecycleOwner, summeryView, toolbarView);
    }

    public MilkTransactionDialogView provideMilkTransactionDialogView(
            @NonNull Context context, @NonNull LifecycleOwner lifecycleOwner) {
        return new MilkTransactionDialogView(context, lifecycleOwner);
    }

    public CustomCalendarView provideCustomCalendarView(
            @NonNull Context context, @NonNull LifecycleOwner lifecycleOwner, CustomCalendarView calendarView) {
        calendarView.injectLifeCycleOwner(context, lifecycleOwner);
        return calendarView;
    }

}
