package com.diary.android.dudhwala.view.customerlist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.view.ILiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.view.SwipeController;
import com.diary.android.dudhwala.view.itemdecoration.CustomItemDecoration;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener;

import java.util.List;

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

public class CustomerListViewImpl implements CustomerListAdapter.CustomerListItemClickListener,
        ILiveDataObserver.CustomerListLiveDataObserver, SwipeController.SwipeActionListener {

    private final String TAG = _TAG + "CustomerListViewImpl";

    private static final int VERTICAL_ITEM_SPACE = 0;

    private Context mContext;
    private LifecycleOwner mLifecycleOwner;
    private RecyclerView mRecyclerView;

    private CustomerListAdapter mCustomerListAdapter;

    @Nullable
    private List<CustomerInfo> mCustomerInfoList;

    private IViewActionListener.CustomerListViewActionListener mViewActionListener;

    public CustomerListViewImpl(Context context,
                                LifecycleOwner lifecycleOwner,
                                View recyclerView) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mRecyclerView = (RecyclerView) recyclerView;

        configureRecyclerView();
    }

    private void configureRecyclerView() {
        Log.d(TAG, "configureRecyclerView()");

        mCustomerListAdapter = new CustomerListAdapter(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //Add swipe actions
        SwipeController swipeController = new SwipeController(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addItemDecoration(new CustomItemDecoration(mContext, R.drawable.divider,
                VERTICAL_ITEM_SPACE, swipeController));
        mRecyclerView.setAdapter(mCustomerListAdapter);

    }

    @Override
    public void startObservingLiveData(ILiveDataSource.CustomerListLiveDataSource liveDataSource,
                                       IViewActionListener.CustomerListViewActionListener viewActionListener) {

        mViewActionListener = viewActionListener;
        liveDataSource.provideCustomerListLiveData().ifPresent(this::setCustomerListLiveData);
    }

    private void setCustomerListLiveData(LiveData<List<CustomerInfo>> customerListLiveData) {
        customerListLiveData.observe(mLifecycleOwner,
                customerInfoList -> {
                    mCustomerInfoList = customerInfoList;
                    mCustomerListAdapter.updateCustomerList(customerInfoList);
                });
    }

    @Override
    public void onClickListItem(int customerId) {
        Log.d(TAG, "onClickListItem() customerId : " + customerId);
        // mViewActionListener.onCustomerListItemClicked(customerId);
        Intent intent = new Intent().setComponent(
                new ComponentName(mContext, Constants.ActivityIntent.MilkTransactionActivity));
        intent.putExtra(Constants.Extra.EXTRA_CUSTOMER_ID, customerId);
        mContext.startActivity(intent);
    }

    @Override
    public void onClickQuickAdd() {
        mViewActionListener.onQuickAddMilkTransactionClicked();
    }

    @Override
    public void onClickAdd() {
        mViewActionListener.onAddMilkTransactionClicked();
    }

    @Override
    public void onEditClicked(int position) {

    }

    @Override
    public void onDeleteClicked(int position) {

    }
}
