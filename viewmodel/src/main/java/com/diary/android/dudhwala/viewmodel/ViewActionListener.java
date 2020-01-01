package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.common.entity.CustomerInfo;

public interface ViewActionListener {

    interface AddEditViewActionListner {
        void onAddCustomerClicked(CustomerInfo customerInfo);
    }

}
