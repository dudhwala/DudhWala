package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfoForMTActivity;
import com.diary.android.dudhwala.view.LiveDataObserver.MillTransactionLiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;

public class MilkTransactionSummeryAndToolbarViewImpl implements
        MillTransactionLiveDataObserver {

    private static final String TAG = "DudhWala/MilkTransactionSummeryAndToolbarViewImpl";
    private final LifecycleOwner mLifecycleOwner;
    private final Context mContext;
    private TextView mSummeryQuantityTextView;
    private TextView mSummeryAmountTextView;
    private TextView mSummeryTotalDueAmountTextView;

    private Toolbar mToolbar;

    public MilkTransactionSummeryAndToolbarViewImpl(Context context, LifecycleOwner lifecycleOwner, View summeryView, View toolbarView) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mSummeryQuantityTextView = summeryView.findViewById(R.id.summeryQuantityTextView);
        mSummeryAmountTextView = summeryView.findViewById(R.id.summeryAmountTextView);
        mSummeryTotalDueAmountTextView = summeryView.findViewById(R.id.summeryTotalAmountDueTextView);
        mToolbar = (Toolbar) toolbarView;

        configureToolbar();
    }

    private void configureToolbar() {
        // Set Toolbar
        ((AppCompatActivity) mContext).setSupportActionBar(mToolbar);

        // Enable the Up button
        ((AppCompatActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                       MilkTransactionViewActionListener viewActionListener) {
        Log.d(TAG, "startObservingLiveData()");
        liveDataSource.provideMilkTransactionSummeryLiveData().ifPresent(this::setSummeryDataObserver);
        liveDataSource.provideCustomerInfoLiveData().ifPresent(this::setCustomerDataObserver);
    }

    private void setCustomerDataObserver(LiveData<CustomerInfoForMTActivity> customerLiveData) {
        customerLiveData.observe(mLifecycleOwner, this::updateCustomerViews);
    }

    private void setSummeryDataObserver(LiveData<SummeryData> milkTransactionLiveData) {
        milkTransactionLiveData.observe(mLifecycleOwner, this::updateSummeryView);
    }

    private void updateSummeryView(SummeryData summeryData) {
        Log.d(TAG, "updateSummeryView()");
        mSummeryQuantityTextView.setText(summeryData.getTotalMilkQuantityInLitersForDuration() + " Litters");
        mSummeryAmountTextView.setText(String.valueOf(summeryData.getTotalAmountForDuration()));
    }

    private void updateCustomerViews(CustomerInfoForMTActivity customerData) {
        Log.d(TAG, "due amount : " + customerData.getTotalAmountDue()
                + " customer name : " + customerData.getCustomerName());
        mSummeryTotalDueAmountTextView.setText(String.valueOf(customerData.getTotalAmountDue()));
        mToolbar.setTitle(customerData.getCustomerName());

    }
}
