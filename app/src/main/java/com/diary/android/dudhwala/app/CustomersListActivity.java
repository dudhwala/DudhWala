package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;

public class CustomersListActivity extends BaseActivity {

    private static final String TAG = "DudhWala/CustomersListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.customers_list_activity);

        //TODO Remove it it is for just testing
        Button b = findViewById(R.id.button);
        b.setOnClickListener(v -> launchTransactionActivity());
    }

    //TODO Remove it it is for just testing
    private void launchTransactionActivity() {
        Intent intent = new Intent(this, MilkTransactionsActivity.class);
        intent.putExtra(Constants.Extra.EXTRA_CUSTOMER_ID, 2);
        startActivity(intent);
    }

    @Override
    void createViewModelAndInjectRepositoryFactory() {

    }

    @Override
    void injectView() {

    }
}
