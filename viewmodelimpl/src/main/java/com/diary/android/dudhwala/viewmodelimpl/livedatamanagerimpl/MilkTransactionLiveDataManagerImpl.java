package com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.diary.android.dudhwala.viewmodel.livedatamanager.IMilkTransactionLiveDataManager;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

public class MilkTransactionLiveDataManagerImpl implements IMilkTransactionLiveDataManager {
    private static final String TAG = _TAG + "MilkTransactionLiveDataManagerImpl";
    private final int mCustomerId;

    private IRepositoryFactory mRepositoryFactory;

    private MediatorLiveData<List<MilkTransaction>> mTransactionsArrayListLiveData = new MediatorLiveData<>();
    private MutableLiveData<SummeryData> mSummeryLiveData = new MutableLiveData<>();
    private MutableLiveData<MilkTransaction> mSelectedMilkTransactionLiveData = new MutableLiveData<>();

    private LiveData<CustomerInfo> mCustomerInfoLiveData;
    private LiveData<List<MilkTransaction>> source;

    public MilkTransactionLiveDataManagerImpl(IRepositoryFactory repositoryFactory, int customerId) {
        mRepositoryFactory = repositoryFactory;
        mCustomerId = customerId;
        mCustomerInfoLiveData = mRepositoryFactory.getCustomerInfoRepository().getCustomerInfo(mCustomerId);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

    @Override
    public void updateMilkTransactionDuration(long fromTimestamp, long toTimestamp) {
        Log.d(TAG, "updateMilkTransactionDuration() fromTimestamp : " + fromTimestamp
                + ", toTimestamp : " + toTimestamp);
        mTransactionsArrayListLiveData.removeSource(source);

        source = mRepositoryFactory.getMilkTransactionRepository()
                .getMilkTransactions(mCustomerId, fromTimestamp, toTimestamp);

        mTransactionsArrayListLiveData.addSource(source, value -> {
            updateSummery(value);
            mTransactionsArrayListLiveData.setValue(value);
        });
    }

    @Override
    public void deleteMilkTransaction(MilkTransaction milkTransaction) {
        Log.d(TAG, "deleteMilkTransaction() milkTransaction : " + milkTransaction.toString());
        mRepositoryFactory.getMilkTransactionRepository().deleteMilkTransaction(milkTransaction);
    }

    @Override
    public LiveData<SummeryData> getSummeryLiveData() {
        return mSummeryLiveData;
    }

    @Override
    public LiveData<CustomerInfo> getCustomerInfoLiveData() {
        return mCustomerInfoLiveData;
    }

    @Override
    public LiveData<List<MilkTransaction>> getTransactionsArrayListLiveData() {
        return mTransactionsArrayListLiveData;
    }

    private void updateSummery(List<MilkTransaction> value) {
        Log.d(TAG, "updateSummery()");
        if (value == null) {
            return;
        }
        Iterator<MilkTransaction> iterator = value.iterator();
        float totalMilkQuantity = 0;
        float totalAmount = 0;

        while (iterator.hasNext()) {
            MilkTransaction milkTransaction = iterator.next();
            totalMilkQuantity += milkTransaction.getMilkQuantityLiters();
            totalAmount += milkTransaction.getTransactionAmount();
        }
        SummeryData summeryData = new SummeryData();
        summeryData.setTotalMilkQuantityInLitersForDuration(totalMilkQuantity);
        summeryData.setTotalAmountForDuration(totalAmount);

        Optional.ofNullable(mSummeryLiveData)
                .ifPresent(summeryLiveData -> summeryLiveData.setValue(summeryData));
    }
}
