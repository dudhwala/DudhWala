package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;

public class CustomersListActivity extends BaseActivity {

    private static final String TAG = "DudhWala/CustomersListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.customers_list_activity);




        findViewById(R.id.fab_add_new_customer).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditCustomerActivity.class);
            intent.putExtra(Constants.Extra.EXTRA_CUSTOMER_ID, 1);
            startActivity(intent);
        });
        tempCode();
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

    }

    @Override
    void injectView() {

    }
}
