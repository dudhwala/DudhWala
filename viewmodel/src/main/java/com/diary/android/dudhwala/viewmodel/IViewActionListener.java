package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.common.Constants;
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

        void onDurationChange(Constants.DurationDirection direction);

        void onClickAddNewMilkTransaction(MilkTransaction newMilkTransaction);

        void onClickEditButton();

        void onClickDelete(MilkTransaction milkTransaction);

        void saveCurrentMilkTransactionState(MilkTransaction mMilkTransaction);

        void updateMilkType(int milkType, long date, int price, int quantity);
    }
}
