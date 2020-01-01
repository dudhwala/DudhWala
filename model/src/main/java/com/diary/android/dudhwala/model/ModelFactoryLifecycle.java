package com.diary.android.dudhwala.model;

public interface ModelFactoryLifecycle {

    interface ViewModelType {
        int noVM = 0x00000000;
        int addEditCustomerVM = 0x00000001;
    }

    boolean connected(int viewmodelType);

    boolean disconnected(int viewmodelType);

}
