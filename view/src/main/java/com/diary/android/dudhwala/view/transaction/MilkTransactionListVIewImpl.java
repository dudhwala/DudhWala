package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.LiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.view.itemdecoration.DividerItemDecoration;
import com.diary.android.dudhwala.view.itemdecoration.VerticalSpaceItemDecoration;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;

import java.util.List;

public class MilkTransactionListVIewImpl implements
        LiveDataObserver.TransactionListLiveDataObserver {


    private static final String TAG = "DudhWala/MilkTransactionListVIewImpl";
    private static final int VERTICAL_ITEM_SPACE = 30;
    private final Context mContext;
    private final LifecycleOwner mLifecycleOwner;
    private final RecyclerView mRecyclerView;
    private MilkTransactionViewActionListener mViewActionListener;
    private MilkTransactionsAdapter mAdapter;

    public MilkTransactionListVIewImpl(Context context, LifecycleOwner lifecycleOwner, View view) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mRecyclerView = (RecyclerView) view;
        mAdapter = new MilkTransactionsAdapter();

        configureRecyclerVIew();
    }

    private void configureRecyclerVIew() {
        Log.d(TAG, "configureRecyclerVIew()");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //add ItemDecoration vertical Space
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        //Add Custom Divider
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mContext, R.drawable.divider));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                       MilkTransactionViewActionListener viewActionListener) {
        Log.d(TAG, "startObservingLiveData()");
        mViewActionListener = viewActionListener;
        liveDataSource.provideMilkTransactionLiveData().ifPresent(this::observeMilkTransactionLiveData);
        mViewActionListener.onListItemClicked(null);

    }

    private void observeMilkTransactionLiveData(LiveData<List<MilkTransaction>> arrayListLiveData) {
        arrayListLiveData.observe(mLifecycleOwner,
                milkTransactions -> {
                    Log.d(TAG, "onChangeMilkTransactions()");
                    mAdapter.updateMilkTransactionsData(milkTransactions);
                });
    }
}
