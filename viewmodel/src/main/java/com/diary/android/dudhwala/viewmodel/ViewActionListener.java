package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;

public interface ViewActionListener {

    interface CustomerListViewActionListener {
        void onCustomerListItemClicked(CustomerInfo customerInfo);

        void onQuickAddMilkTransactionClicked();

        void onAddMilkTransactionClicked();
    }

    interface AddEditViewActionListner {
        void onAddCustomerClicked(CustomerData customerData);
    }

    interface MilkTransactionViewActionListener {

        void onDurationChange(long fromTimeStamp, long toTimestamp);

        void onClickChangeDuration(Constants.DurationDirection direction);

        void onListItemClicked(MilkTransaction milkTransaction);

        void onClickAddNewMilkTransaction(MilkTransaction newMilkTransaction);

        void onClickEditButton();

        void onClickDeleteButton();

        void saveCurrentMilkTransactionState(MilkTransaction mMilkTransaction);

        void updateMilkType(int milkType, long date, int price, int quantity);
    }
}
