package com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.MilkType;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.RepositoryFactory;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager.DialogLiveDataManager;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager.SummeryLiveDataManager;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager.TransactionsListLiveDataManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MilkTransactionLiveDataManagerImpl implements
        MilkTransactionLiveDataManager, DialogLiveDataManager, SummeryLiveDataManager,
        TransactionsListLiveDataManager {

    private static final String TAG = "DudhWala/MilkTransactionLiveDataManagerImpl";
    private static final int DEFAULT_MILK_PRICE = 50;
    private final int mCustomerId;

    private RepositoryFactory mRepositoryFactory;
    private MediatorLiveData<List<MilkTransaction>> mTransactionsArrayListLiveData
            = new MediatorLiveData<>();

    private LiveData<List<MilkTransaction>> source;

    private HashMap milkTransactionCache;
    private MutableLiveData<SummeryData> mSummeryLiveData = new MutableLiveData<>();
    private MutableLiveData<MilkTransaction> mSelectedMilkTransactionLiveData = new MutableLiveData<>();
    private LiveData<CustomerInfo> mCustomerInfoLiveData;

    public MilkTransactionLiveDataManagerImpl(RepositoryFactory repositoryFactory, int customerId) {
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
    public LiveData<MilkTransaction> getMilkTransactionLiveData(int milkTransactionId) {
        return null;
    }

    @Override
    public void insertNewMilkTransaction(MilkTransaction milkTransaction) {
        mRepositoryFactory.getMilkTransactionRepository().insertMilkTransaction(milkTransaction);
    }

    @Override
    public void saveCurrentMilkTransactionState(MilkTransaction milkTransaction) {
        mSelectedMilkTransactionLiveData.setValue(milkTransaction);
    }

    @Override
    public void updateMilkType(int milkType, long date, int price, int quantity) {

        //old price not required any more
        price = getPriceOfMilkType(milkType);
        MilkTransaction milkTransaction = new MilkTransaction(
                mCustomerId, quantity, milkType, price, price * quantity, date);
        mSelectedMilkTransactionLiveData.setValue(milkTransaction);
    }

    private int getPriceOfMilkType(int milkType) {
        CustomerInfo customerInfo = mCustomerInfoLiveData.getValue();
        if (customerInfo != null) {
            if (milkType == MilkType.COW.intValue()) {
                return customerInfo.getPricePerLiterCow();
            } else if (milkType == MilkType.BUFF.intValue()) {
                return customerInfo.getPricePerLiterBuffalo();
            } else if (milkType == MilkType.MIX.intValue()) {
                return customerInfo.getPricePerLiterMix();
            }
        }
        return DEFAULT_MILK_PRICE;
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
        Log.d(TAG, "getTransactionsArrayListLiveData()");
        return mTransactionsArrayListLiveData;
    }

    @Override
    public LiveData<MilkTransaction> getSelectedMilkTransaction() {
        Log.d(TAG, "getSelectedMilkTransaction()");
        return mSelectedMilkTransactionLiveData;
    }

    @Override
    public void updateTransactionId(int transactionId) {
        if (transactionId == Constants.MilkTransactionConstants.UNKNOWN_TRANSACTION_ID) {
            mSelectedMilkTransactionLiveData.setValue(getDefaultMilkTransaction());
        } else {
            mSelectedMilkTransactionLiveData.setValue(getMTForTransactionId(transactionId));
        }
    }

    @Override
    public void updateCurrentMilkTransaction(MilkTransaction milkTransaction) {
        mSelectedMilkTransactionLiveData.setValue(milkTransaction);
    }

    private MilkTransaction getDefaultMilkTransaction() {
        CustomerInfo customerInfo = mCustomerInfoLiveData.getValue();
        int pricePerLiter = getPriceOfDefaultMilkType(customerInfo);
        int transactionAmount = customerInfo.getQuickAddMilkType() * pricePerLiter;
        long transactionDate = System.currentTimeMillis();

        MilkTransaction milkTransaction = new MilkTransaction(
                customerInfo.getId(),
                customerInfo.getQuickAddQuantity(),
                customerInfo.getQuickAddMilkType(),
                pricePerLiter,
                transactionAmount,
                transactionDate);

        return milkTransaction;
    }

    private int getPriceOfDefaultMilkType(CustomerInfo customerInfo) {

        if (customerInfo.getQuickAddMilkType() == MilkType.COW.intValue()) {
            return customerInfo.getPricePerLiterCow();
        } else if (customerInfo.getQuickAddMilkType() == MilkType.BUFF.intValue()) {
            return customerInfo.getPricePerLiterBuffalo();
        } else {
            return customerInfo.getPricePerLiterMix();
        }
    }

    private MilkTransaction getMTForTransactionId(int transactionId) {
        return null;
    }

    @Override
    public void updateMilkTransactionDuration(long fromTimestamp, long toTimestamp) {
        Log.d(TAG, "updateMilkTransactionDuration() mCustomerId : " + mCustomerId);

        source = mRepositoryFactory.getMilkTransactionRepository()
                .getMilkTransactions(mCustomerId, fromTimestamp, toTimestamp);

        mTransactionsArrayListLiveData.addSource(source, value -> {
            updateSummery(value);
            mTransactionsArrayListLiveData.setValue(value);
        });
    }

    @Override
    public void deleteMilkTransaction(MilkTransaction milkTransaction) {
        mRepositoryFactory.getMilkTransactionRepository().deleteMilkTransaction(milkTransaction);
    }

    private void updateSummery(List<MilkTransaction> value) {
        Log.d(TAG, "updateSummery()");
        if (value == null) {
            return;
        }
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
