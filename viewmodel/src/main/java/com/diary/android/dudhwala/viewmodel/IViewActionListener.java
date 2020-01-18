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

        void onClickEditButton();

        void onClickDelete(MilkTransaction milkTransaction);

        void onClickUndoMilkTransaction(MilkTransaction milkTransaction);

    }

    interface MilkTransactionDFViewActionListener {

        void onClickAddMilkTransaction(MilkTransaction newMilkTransaction);

        void saveCurrentMilkTransactionState(MilkTransaction mMilkTransaction);

        void updateMilkType(MilkTransaction milkTransaction);

    }

    interface ICustomCalendarViewActionListener {
        void initializeCalendar();

        void onClickCustomCalenderButton(int button);

        void onDurationChange(int month, int year);
    }
}
