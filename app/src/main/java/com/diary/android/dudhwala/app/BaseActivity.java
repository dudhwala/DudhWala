package com.diary.android.dudhwala.app;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    abstract void createViewModelAndInjectRepositoryFactory();

    abstract void injectView();
}
