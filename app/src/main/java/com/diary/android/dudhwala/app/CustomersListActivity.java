package com.diary.android.dudhwala.app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.diary.android.dudhwala.R;

public class CustomersListActivity extends AppCompatActivity {

    private static final String TAG = "DudhWala/CustomersListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);
    }
}
