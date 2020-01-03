package com.diary.android.dudhwala.modelimpl.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.diary.android.dudhwala.common.CommonThreadPool;
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
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //insert dummy data
            CommonThreadPool.getThreadPool().execute(() -> {
                        MilkTransactionDao milkTransactionDao = INSTANCE.milkTransactionDao();
                        milkTransactionDao.insertMilkTransaction(new MilkTransaction(2,
                                2, 1, 50, 100,
                                System.currentTimeMillis()));
                milkTransactionDao.insertMilkTransaction(new MilkTransaction(2,
                        3, 1, 50, 100,
                        System.currentTimeMillis()));
                milkTransactionDao.insertMilkTransaction(new MilkTransaction(2,
                        4, 1, 50, 100,
                        System.currentTimeMillis()));


                        PaymentDao paymentDao = INSTANCE.paymentDao();

                        paymentDao.insertPayment(new Payment(2, 600,
                                System.currentTimeMillis()));

                        MonthTransactionAmountDao monthTransactionAmountDao = INSTANCE.monthTransactionAmountDao();
                        monthTransactionAmountDao.insertMonthTransactionAmount(new MonthTransactionAmount(2,
                                1800, 12, 2019));

                        CustomerInfoDao customerInfoDao = INSTANCE.customerInfoDao();
                        customerInfoDao.insertCustomerInfo(new CustomerInfo("Rd Rundla",
                                "9983262200",
                                "rdrundla@gmail.com",
                                "K 1007",
                                50,
                                60,
                                55,
                                2,
                                2,
                                600,
                                System.currentTimeMillis()));
                    }
            );
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

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

    public abstract MilkTransactionDao milkTransactionDao();

    public abstract CustomerInfoDao customerInfoDao();

    public abstract MonthTransactionAmountDao monthTransactionAmountDao();

    public abstract PaymentDao paymentDao();
}
