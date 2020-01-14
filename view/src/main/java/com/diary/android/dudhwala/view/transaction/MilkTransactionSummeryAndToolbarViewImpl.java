package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.view.ILiveDataObserver.MillTransactionLiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionViewActionListener;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MilkTransactionSummeryAndToolbarViewImpl implements
        MillTransactionLiveDataObserver {

    private static final String TAG = "DudhWala/MilkTransactionSummeryAndToolbarViewImpl";
    private final LifecycleOwner mLifecycleOwner;
    private final Context mContext;
    private TextView mSummeryQuantityTextView;
    private TextView mSummeryAmountTextView;
    private TextView mSummeryTotalDueAmountTextView;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    public MilkTransactionSummeryAndToolbarViewImpl(Context context, LifecycleOwner lifecycleOwner, View summeryView, View toolbarView) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mSummeryQuantityTextView = summeryView.findViewById(R.id.summeryQuantityTextView);
        mSummeryAmountTextView = summeryView.findViewById(R.id.summeryAmountTextView);
        mSummeryTotalDueAmountTextView = summeryView.findViewById(R.id.summeryTotalAmountDueTextView);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) toolbarView;
        mToolbar = mCollapsingToolbarLayout.findViewById(R.id.toolbar);

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
        //Log.d(TAG, "startObservingLiveData()");
        liveDataSource.provideMilkTransactionSummeryLiveData().ifPresent(this::setSummeryDataObserver);
        liveDataSource.provideCustomerInfoLiveData().ifPresent(this::setCustomerDataObserver);
    }

    private void setCustomerDataObserver(LiveData<CustomerInfo> customerInfoLiveData) {
        customerInfoLiveData.observe(mLifecycleOwner, this::updateCustomerViews);
    }

    private void setSummeryDataObserver(LiveData<SummeryData> milkTransactionLiveData) {
        milkTransactionLiveData.observe(mLifecycleOwner, this::updateSummeryView);
    }

    private void updateSummeryView(SummeryData summeryData) {
        mSummeryQuantityTextView.setText(String.format("%s Litters", summeryData.getTotalMilkQuantityInLitersForDuration()));
        mSummeryAmountTextView.setText(String.valueOf(summeryData.getTotalAmountForDuration()));
    }

    private void updateCustomerViews(CustomerInfo customerData) {
        Log.d(TAG, "updateCustomerViews() Amount due : " + customerData.getTotalAmountDue()
                + " Customer name : " + customerData.getCustomerName());
        mSummeryTotalDueAmountTextView.setText(String.valueOf(customerData.getTotalAmountDue()));
        mCollapsingToolbarLayout.setTitle(customerData.getCustomerName());

    }
}
