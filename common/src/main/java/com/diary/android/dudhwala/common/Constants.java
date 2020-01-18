package com.diary.android.dudhwala.common;

public interface Constants {
    interface Extra {
        String EXTRA_CUSTOMER_ID = "extra_customer_id";
    }

    interface ClickedButton {
        int NEXT = 1;
        int PREVIOUS = 2;
        int CENTER = 3;
    }

    interface Customer {
        String CUSTOMER_ID = "customer_id";
        int UNKNOWN_CUSTOMER_ID = -1;
        int PRICE_UNKNOWN = 0;
        int DEFAULT_QUICK_ADD_QUANTITY = 2;
    }

    interface MilkTransactionConstants {
        String TRANSACTION_STRING = "transaction_id";
        int UNKNOWN_TRANSACTION_ID = -1;
    }

    interface Log {
        String _TAG = "DudhWala/";
    }
    interface ActivityIntent{
        String MilkTransactionActivity = "com.diary.android.dudhwala.app.MilkTransactionsActivity";
        String AddEditCustomerActivity = "com.diary.android.dudhwala.app.AddEditCustomerActivity";
        String PaymentsActivity = "com.diary.android.dudhwala.app.PaymentsActivity";

    }

}
