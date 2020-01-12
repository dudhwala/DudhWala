package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;

public interface IViewActionListener {

    interface CustomerListViewActionListener {
        void onCustomerListItemClicked(CustomerInfo customerInfo);

        void onQuickAddMilkTransactionClicked();

        void onAddMilkTransactionClicked();
    }

    interface AddEditViewActionListener {
        void onAddCustomerClicked(CustomerData customerData);
    }

    interface MilkTransactionViewActionListener {

        void onDurationChange(int month, int year);

        void onClickAddMilkTransaction(MilkTransaction newMilkTransaction);

        void onClickEditButton();

        void onClickDelete(MilkTransaction milkTransaction);

        void saveCurrentMilkTransactionState(MilkTransaction mMilkTransaction);

        void updateMilkType(int milkType, long date, float price, float quantity);
    }

    interface ICustomCalendarActionListener {
        void onClickButton(int button);

        void initializeCalendar();
    }
}
