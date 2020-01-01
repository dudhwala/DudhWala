package com.diary.android.dudhwala.app;

import android.app.Application;

import com.diary.android.dudhwala.model.ModelFactory;
import com.diary.android.dudhwala.modelimpl.ModelFactoryImpl;

public class App extends Application {

    private static App instance;
    private ModelFactory modelFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    static App getInstance() {
        return instance;
    }

    // So that model be same for all components
    ModelFactory getModelFactory(){
        if(modelFactory == null){
            modelFactory = new ModelFactoryImpl(this);
        }
        return modelFactory;
    }

}
