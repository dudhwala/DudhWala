package com.diary.android.dudhwala.viewmodel;


import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;

import java.util.Optional;

public interface LiveDataSource {

    interface AddEditLiveDataSource {
        default Optional<LiveData<CustomerInfo>> provideCustomerInfoLiveData() {
            return Optional.empty();
        }
    }

}
