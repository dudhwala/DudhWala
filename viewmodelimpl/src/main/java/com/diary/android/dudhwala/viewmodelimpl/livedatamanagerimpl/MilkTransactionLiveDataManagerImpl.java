package com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.RepositoryFactory;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MilkTransactionLiveDataManagerImpl implements
        MilkTransactionLiveDataManager,
        MilkTransactionLiveDataManager.DetailDialogLiveDataManager,
        MilkTransactionLiveDataManager.SummeryLiveDataManager,
        MilkTransactionLiveDataManager.TransactionsListLiveDataManager {

    private static final String TAG = "DudhWala/MilkTransactionLiveDataManagerImpl";
    private final int mCustomerId;

    private RepositoryFactory mRepositoryFactory;
    private MediatorLiveData<List<MilkTransaction>> mTransactionsArrayListLiveData
            = new MediatorLiveData<>();

    private HashMap milkTransactionCache;
    private MutableLiveData<SummeryData> mSummeryLiveData = new MutableLiveData<>();

    public MilkTransactionLiveDataManagerImpl(RepositoryFactory repositoryFactory, int customerId) {
        mRepositoryFactory = repositoryFactory;
        mCustomerId = customerId;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

    @Override
    public LiveData<Boolean> getDetailDialogVisibilityControllerLiveData() {
        return null;
    }

    @Override
    public LiveData<MilkTransaction> getMilkTransactionLiveData(int milkTransactionId) {
        return null;
    }

    @Override
    public LiveData<SummeryData> getSummeryLiveData() {
        return mSummeryLiveData;
    }

    @Override
    public LiveData<List<MilkTransaction>> getTransactionsArrayListLiveData() {
        Log.d(TAG, "getTransactionsArrayListLiveData()");
        return mTransactionsArrayListLiveData;
    }

    @Override
    public void updateMilkTransactionDuration(long fromTimestamp, long toTimestamp) {
        Log.d(TAG, "updateMilkTransactionDuration()");

        LiveData<List<MilkTransaction>> source = mRepositoryFactory.getMilkTransactionRepository()
                .getMilkTransactions(mCustomerId, fromTimestamp, toTimestamp);

        mTransactionsArrayListLiveData.addSource(source,
                value -> {
                    updateSummery(value);
                    mTransactionsArrayListLiveData.setValue(value);
                });
    }

    private void updateSummery(List<MilkTransaction> value) {
        Log.d(TAG, "updateSummery()");
        Iterator<MilkTransaction> iterator = value.iterator();
        int totalMilkQuantity = 0;
        int totalAmount = 0;

        while (iterator.hasNext()) {
            MilkTransaction milkTransaction = iterator.next();
            totalMilkQuantity += milkTransaction.getMilkQuantityLiters();
            totalAmount += milkTransaction.getTransactionAmount();
        }
        SummeryData summeryData = new SummeryData();
        summeryData.setTotalMilkQuantityInLitersForDuration(totalMilkQuantity);
        summeryData.setTotalAmountForDuration(totalAmount);

        mSummeryLiveData.setValue(summeryData);
    }
}
