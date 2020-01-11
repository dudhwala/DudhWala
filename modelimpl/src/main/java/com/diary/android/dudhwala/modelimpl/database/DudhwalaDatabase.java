package com.diary.android.dudhwala.modelimpl.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.diary.android.dudhwala.common.entity.Action;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.common.entity.MonthTransactionAmount;
import com.diary.android.dudhwala.common.entity.Payment;
import com.diary.android.dudhwala.modelimpl.dao.CustomerInfoDao;
import com.diary.android.dudhwala.modelimpl.dao.MilkTransactionDao;
import com.diary.android.dudhwala.modelimpl.dao.MonthTransactionAmountDao;
import com.diary.android.dudhwala.modelimpl.dao.PaymentDao;

@Database(entities = {CustomerInfo.class, MilkTransaction.class, Payment.class,
        MonthTransactionAmount.class, Action.class},
        version = 1,
        exportSchema = false)
public abstract class DudhwalaDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "dudhwala_database";
    private static volatile DudhwalaDatabase INSTANCE;

    private static final String CREATE_TRIGGER_AFTER_INSERT_TOTAL_DUE_AMOUNT_AND_LAST_UPDATE_TIME =
            "CREATE TRIGGER update_customer_due_amount " +
                    "AFTER INSERT ON milk_transaction_table " +
                    "BEGIN " +
                    "UPDATE customer_info_table " +
                    "SET total_amount_due = total_amount_due + NEW.transaction_amount, " +
                    "last_updated_timestamp = NEW.created_time_stamp " +
                    "WHERE _id = NEW.customer_id; " +
                    "END;";

    private static final String CREATE_TRIGGER_AFTER_UPDATE_TOTAL_DUE_AMOUNT_AND_LAST_UPDATE_TIME =
            "CREATE TRIGGER update_customer_due_amount_after_update " +
                    "AFTER UPDATE ON milk_transaction_table " +
                    "BEGIN " +
                    "UPDATE customer_info_table " +
                    "SET total_amount_due = total_amount_due + NEW.transaction_amount - OLD.transaction_amount, " +
                    "last_updated_timestamp = NEW.created_time_stamp " +
                    "WHERE _id = NEW.customer_id; " +
                    "END;";

    private static final String CREATE_TRIGGER_AFTER_DELETE_TOTAL_DUE_AMOUNT_AND_LAST_UPDATE_TIME =
            "CREATE TRIGGER update_customer_due_amount_after_delete " +
                    "AFTER DELETE ON milk_transaction_table " +
                    "BEGIN " +
                    "UPDATE customer_info_table " +
                    "SET total_amount_due = total_amount_due - OLD.transaction_amount " +
                    "WHERE _id = OLD.customer_id; " +
                    "END;";

    public static DudhwalaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DudhwalaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DudhwalaDatabase.class,
                            DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL(CREATE_TRIGGER_AFTER_INSERT_TOTAL_DUE_AMOUNT_AND_LAST_UPDATE_TIME);
            db.execSQL(CREATE_TRIGGER_AFTER_UPDATE_TOTAL_DUE_AMOUNT_AND_LAST_UPDATE_TIME);
            db.execSQL(CREATE_TRIGGER_AFTER_DELETE_TOTAL_DUE_AMOUNT_AND_LAST_UPDATE_TIME);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public abstract MilkTransactionDao milkTransactionDao();

    public abstract CustomerInfoDao customerInfoDao();

    public abstract MonthTransactionAmountDao monthTransactionAmountDao();

    public abstract PaymentDao paymentDao();
}
