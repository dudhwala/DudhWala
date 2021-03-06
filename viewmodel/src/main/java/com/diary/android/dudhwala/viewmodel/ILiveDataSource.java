package com.diary.android.dudhwala.viewmodel;


import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.viewmodel.data.DurationData;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;

import java.util.List;
import java.util.Optional;

public interface ILiveDataSource {

    interface CustomerListLiveDataSource {
        default Optional<LiveData<List<CustomerInfo>>> provideCustomerListLiveData() {
            return Optional.empty();
        }
    }

    interface AddEditLiveDataSource {
        default Optional<LiveData<CustomerInfo>> provideCustomerInfoLiveData() {
            return Optional.empty();
        }
    }

    interface MilkTransactionLiveDataSource {
        default Optional<LiveData<List<MilkTransaction>>> provideMilkTransactionListLiveData() {
            return Optional.empty();
        }

        default Optional<LiveData<SummeryData>> provideMilkTransactionSummeryLiveData() {
            return Optional.empty();
        }

        default Optional<LiveData<CustomerInfo>> provideCustomerInfoLiveData() {
            return Optional.empty();
        }
    }

    interface ICustomCalendarLiveDataSource {
        default Optional<LiveData<DurationData>> provideDurationLiveData() {
            return Optional.empty();
        }
    }

    interface MilkTransactionDFLiveDataSource {
        default Optional<LiveData<MilkTransaction>> provideSelectedMilkTransactionLiveData() {
            return Optional.empty();
        }
    }

}
