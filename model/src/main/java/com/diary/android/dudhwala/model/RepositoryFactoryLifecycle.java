package com.diary.android.dudhwala.model;

public interface RepositoryFactoryLifecycle {

    interface ViewModelType {
        int noVM = 0x00000000;
        int addEditCustomerVM = 0x00000001;
    }

    boolean connected(int viewModelType);

    boolean disconnected(int viewModelType);

}
