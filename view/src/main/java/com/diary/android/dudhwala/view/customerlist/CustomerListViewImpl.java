package com.diary.android.dudhwala.view.customerlist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.view.LiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.view.itemdecoration.CustomItemDecoration;
import com.diary.android.dudhwala.viewmodel.LiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener;

import java.util.List;

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

public class CustomerListViewImpl implements CustomerListAdapter.CustomerListItemClickListener,
        LiveDataObserver.CustomerListLiveDataObserver {

    private final String TAG = _TAG + "CustomerListViewImpl";

    private static final int VERTICAL_ITEM_SPACE = 30;

    private Context mContext;
    private LifecycleOwner mLifecycleOwner;
    private RecyclerView mRecyclerView;

    private CustomerListAdapter mCustomerListAdapter;

    @Nullable
    private List<CustomerInfo> mCustomerInfoList;

    private ViewActionListener.CustomerListViewActionListener mViewActionListener;

    public CustomerListViewImpl(Context context,
                                LifecycleOwner lifecycleOwner,
                                View recyclerView) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        mRecyclerView = (RecyclerView) recyclerView;

        configureRecyclerView();
    }

    private void configureRecyclerView() {
        mCustomerListAdapter = new CustomerListAdapter(this);
        Log.d(TAG, "configureRecyclerVIew()");
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new CustomItemDecoration(mContext, R.drawable.divider, VERTICAL_ITEM_SPACE));
        mRecyclerView.setAdapter(mCustomerListAdapter);

    }

    @Override
    public void startObservingLiveData(LiveDataSource.CustomerListLiveDataSource liveDataSource,
                                       ViewActionListener.CustomerListViewActionListener viewActionListener) {

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
    public void onClickListItem() {
        Intent intent = new Intent().setComponent(
                new ComponentName(mContext, "com.diary.android.dudhwala.app.MilkTransactionsActivity"));
        intent.putExtra(Constants.Extra.EXTRA_CUSTOMER_ID, 2);
        mContext.startActivity(intent);
    }

    @Override
    public void onClickQuickAdd() {
        Toast.makeText(mContext, "quickadd", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickAdd() {
        Toast.makeText(mContext, "add", Toast.LENGTH_SHORT).show();
    }
}
