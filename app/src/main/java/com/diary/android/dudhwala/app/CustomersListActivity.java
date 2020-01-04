package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.viewmodel.CustomerListViewModel;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.CustomerListViewModelImpl;

public class CustomersListActivity extends BaseActivity {

    private static final String TAG = "DudhWala/CustomersListActivity";

    private CustomerListViewModel mCustomerListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.customers_list_activity);

        createViewModelAndInjectRepositoryFactory();
        injectView();

        findViewById(R.id.fab_add_new_customer).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditCustomerActivity.class);
            intent.putExtra(Constants.Extra.EXTRA_CUSTOMER_ID, 1);
            startActivity(intent);
        });
        tempCode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //injectView();
    }

    private CustomerListViewModel getViewModel() {
        return ViewModelProviders.of(this).get(CustomerListViewModelImpl.class);
    }

    //TODO Remove it it is for just testing
    private void tempCode() {
        findViewById(R.id.fab_temp).setOnClickListener(v -> {
            Intent intent = new Intent(this, MilkTransactionsActivity.class);
            intent.putExtra(Constants.Extra.EXTRA_CUSTOMER_ID, 2);
            startActivity(intent);
        });
    }

    @Override
    void createViewModelAndInjectRepositoryFactory() {

        mCustomerListViewModel = getViewModel();
        if (mCustomerListViewModel.isNewInstance()) {
            mCustomerListViewModel.markAsOldInstance();
            mCustomerListViewModel.injectRepositoryFactory(App.getInstance().getRepositoryFactory());
            mCustomerListViewModel.injectLiveDataManager();
        }
    }

    @Override
    void injectView() {
        ViewFactory viewFactory = ViewFactory.getViewFactoryInstance();

        viewFactory.provideCustomerListView(this,
                this,
                findViewById(R.id.recyclerView_customer_list))
                .startObservingLiveData(mCustomerListViewModel, mCustomerListViewModel);

    }
}
