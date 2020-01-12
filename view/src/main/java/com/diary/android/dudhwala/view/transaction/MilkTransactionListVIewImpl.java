package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.ILiveDataObserver.MillTransactionLiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.view.SwipeController;
import com.diary.android.dudhwala.view.itemdecoration.CustomItemDecoration;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionViewActionListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MilkTransactionListVIewImpl implements
        MillTransactionLiveDataObserver, SwipeController.SwipeActionListener {

    private static final String TAG = "DudhWala/MilkTransactionListVIewImpl";
    private static final int VERTICAL_ITEM_SPACE = 0;
    public static final int SNACK_BAR_DURATION_SHORT = 2000;
    private final Context mContext;
    private final LifecycleOwner mLifecycleOwner;
    private final RecyclerView mRecyclerView;
    private final TextView mEmptyView;
    private final View mView;
    private MilkTransactionViewActionListener mViewActionListener;
    private MilkTransactionsAdapter mAdapter;

    public MilkTransactionListVIewImpl(Context context, LifecycleOwner lifecycleOwner, View view) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mAdapter = new MilkTransactionsAdapter();

        mView = view;
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mEmptyView = view.findViewById(R.id.empty_view);
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        Log.d(TAG, "configureRecyclerVIew()");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);

        //Reverse View Show latest transaction at bottom
        linearLayoutManager.setStackFromEnd(true);
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
        Log.d(TAG, "startObservingLiveData()");
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
        //todo
    }

    @Override
    public void onDeleteClicked(int position) {
        Log.d(TAG, "onDeleteClicked() show snack com.google.android.materialbar to undo");
        MilkTransaction milkTransaction = mAdapter.getItem(position);
        mViewActionListener.removeItemAtPosition(position);

        Timer timer = new Timer();
        Snackbar snackbar = Snackbar.make(mView, "Item deleted", Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.undo, v -> {
            Log.d(TAG, "Snack bar clicked undo delete");
            timer.cancel();
            mViewActionListener.addItemAtPosition(position, milkTransaction);
        });
        snackbar.show();

        timer.schedule(new TimerTask() {
            public void run() {
                Log.d(TAG, "Snack bar disappeared, run delete task.");
                mViewActionListener.onClickDelete(milkTransaction);
            }
        }, SNACK_BAR_DURATION_SHORT);

    }
}
