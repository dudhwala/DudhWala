package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.app.MilkTransactionDialogFragment;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.ILiveDataObserver.MillTransactionLiveDataObserver;
import com.diary.android.dudhwala.view.SwipeController;
import com.diary.android.dudhwala.view.itemdecoration.CustomItemDecoration;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionViewActionListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MilkTransactionListVIewImpl implements
        MillTransactionLiveDataObserver, SwipeController.SwipeActionListener {

    private static final String TAG = "DudhWala/MilkTransactionListViewImpl";
    private static final int VERTICAL_ITEM_SPACE = 0;
    private final Context mContext;
    private final LifecycleOwner mLifecycleOwner;
    private final RecyclerView mRecyclerView;
    private final NestedScrollView mEmptyView;
    private final CoordinatorLayout mView;
    private final int mCustomerId;
    private MilkTransactionViewActionListener mViewActionListener;
    private MilkTransactionsAdapter mAdapter;

    public MilkTransactionListVIewImpl(Context context, LifecycleOwner lifecycleOwner, View view,
                                       int customerId) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mCustomerId = customerId;
        mAdapter = new MilkTransactionsAdapter();

        mView = (CoordinatorLayout) view;
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mEmptyView = view.findViewById(R.id.empty_view);
        view.findViewById(R.id.add_fab).setOnClickListener(v -> showAddEditMilkTransactionDialog(
                Constants.MilkTransactionConstants.UNKNOWN_TRANSACTION_ID, mCustomerId));

        configureRecyclerView();
    }

    private void configureRecyclerView() {
        //Log.d(TAG, "configureRecyclerVIew()");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);

        //Reverse View Show latest transaction at bottom
        //linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //Add swipe actions
        SwipeController swipeController = new SwipeController(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        //Add Custom Divider and ItemDecoration vertical Space and dismiss swipe action on scroll
        mRecyclerView.addItemDecoration(new CustomItemDecoration(mContext, swipeController, VERTICAL_ITEM_SPACE));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                       MilkTransactionViewActionListener viewActionListener) {
        //Log.d(TAG, "startObservingLiveData()");
        mViewActionListener = viewActionListener;
        liveDataSource.provideMilkTransactionListLiveData().ifPresent(this::observeMilkTransactionLiveData);
    }

    private void observeMilkTransactionLiveData(LiveData<List<MilkTransaction>> arrayListLiveData) {
        arrayListLiveData.observe(mLifecycleOwner,
                milkTransactions -> {
                    Log.d(TAG, "onChangeMilkTransactions()");
                    if (milkTransactions.size() == 0) {
                        mRecyclerView.setVisibility(View.GONE);
                        mEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mEmptyView.setVisibility(View.GONE);
                    }
                    mAdapter.updateMilkTransactionsData(milkTransactions);
                });
    }

    @Override
    public void onEditClicked(int position) {
        Log.d(TAG, "onEditClicked() position : " + position);
        MilkTransaction milkTransaction = mAdapter.getItem(position);
        showAddEditMilkTransactionDialog(milkTransaction.getId(), milkTransaction.getCustomerId());
    }

    private void showAddEditMilkTransactionDialog(int transactionId, int customerId) {
        Log.d(TAG, "showAddEditMilkTransactionDialog()");
        MilkTransactionDialogFragment milkTransactionDialogFragment = new MilkTransactionDialogFragment();

        Bundle args = new Bundle();
        args.putInt(Constants.Customer.CUSTOMER_ID, customerId);
        args.putInt(Constants.MilkTransactionConstants.TRANSACTION_STRING, transactionId);
        milkTransactionDialogFragment.setArguments(args);

        milkTransactionDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(),
                "add_edit_milk_transaction_dialog");
    }

    @Override
    public void onDeleteClicked(int position) {
        Log.d(TAG, "onDeleteClicked() show snack bar to undo");

        MilkTransaction milkTransaction = mAdapter.getItem(position);
        mViewActionListener.onClickDelete(milkTransaction);

        Snackbar.make(mView, R.string.item_deleted_text, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    Log.d(TAG, "onClickUndo delete");
                    mViewActionListener.onClickUndoMilkTransaction(milkTransaction);
                })
                .show();
    }
}
