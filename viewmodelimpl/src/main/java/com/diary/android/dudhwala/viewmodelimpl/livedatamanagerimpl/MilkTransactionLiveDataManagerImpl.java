package com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.RepositoryFactory;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager;

import java.util.HashMap;
import java.util.List;

public class MilkTransactionLiveDataManagerImpl implements
        MilkTransactionLiveDataManager,
        MilkTransactionLiveDataManager.DetailDialogLiveDataManager,
        MilkTransactionLiveDataManager.SummeryLiveDataManager,
        MilkTransactionLiveDataManager.TransactionsListLiveDataManager {

    private static final String TAG = "DudhWala/MilkTransactionLiveDataManagerImpl";

    private RepositoryFactory mRepositoryFactory;
    private MediatorLiveData<List<MilkTransaction>> mTransactionsArrayListLiveData
            = new MediatorLiveData<>();

    private HashMap milkTransactionCache;

    public MilkTransactionLiveDataManagerImpl(RepositoryFactory repositoryFactory) {
        mRepositoryFactory = repositoryFactory;
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
    public LiveData<MilkTransaction> getSummeryLiveData() {
        return null;
    }

    @Override
    public LiveData<List<MilkTransaction>> getTransactionsArrayListLiveData() {
        Log.d(TAG, "getTransactionsArrayListLiveData()");
        return mTransactionsArrayListLiveData;
    }

    @Override
    public void onDurationChanged(long fromTimestamp, long toTimestamp) {

    }

    @Override
    public void onClickDurationChange(Constants.DurationDirection direction) {
        Log.d(TAG, "onClickDurationChange() direction : " + direction);
        //TODO calculate from and to timestamp according to direction
        long fromTimestamp = 0;
        long toTimestamp = System.currentTimeMillis();

        LiveData<List<MilkTransaction>> source = mRepositoryFactory.getMilkTransactionRepository()
                .getMilkTransactions(fromTimestamp, toTimestamp);

        mTransactionsArrayListLiveData.addSource(source,
                value -> mTransactionsArrayListLiveData.setValue(value));
    }
}
