package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;

public interface ViewActionListener {

    interface AddEditViewActionListner {
        void onAddCustomerClicked(CustomerData customerData);
    }

    interface MilkTransactionViewActionListener {

        void onDurationChange(long fromTimeStamp, long toTimestamp);

        void onClickChangeDuration(Constants.DurationDirection direction);

        void onListItemClicked(MilkTransaction milkTransaction);

        void onClickDialogPositiveButton(MilkTransaction newMilkTransaction);

        void onClickEditButton();

        void onClickDeleteButton();
    }
}
