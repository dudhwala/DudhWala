package com.diary.android.dudhwala.viewmodel.executor;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.viewmodel.executorlifecycle.ExecutorLifeCycle;

public interface AddEditCustomerExecutor extends ExecutorLifeCycle {

    LiveData<CustomerInfo> getCustomerInfoLiveData();

}
