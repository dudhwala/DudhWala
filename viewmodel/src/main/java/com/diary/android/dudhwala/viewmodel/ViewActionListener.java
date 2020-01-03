package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.viewmodel.data.CustomerData;

public interface ViewActionListener {

    interface AddEditViewActionListner {
        void onAddCustomerClicked(CustomerData customerData);
    }

}
