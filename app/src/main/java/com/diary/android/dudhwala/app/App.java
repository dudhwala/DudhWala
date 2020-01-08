package com.diary.android.dudhwala.app;

import android.app.Application;

import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.modelimpl.RepositoryFactoryImpl;

public class App extends Application {

    private static App instance;
    private IRepositoryFactory mRepositoryFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    static App getInstance() {
        return instance;
    }

    // So that model be same for all components
    IRepositoryFactory getRepositoryFactory() {
        if (mRepositoryFactory == null) {
            mRepositoryFactory = new RepositoryFactoryImpl(this);
        }
        return mRepositoryFactory;
    }

}
