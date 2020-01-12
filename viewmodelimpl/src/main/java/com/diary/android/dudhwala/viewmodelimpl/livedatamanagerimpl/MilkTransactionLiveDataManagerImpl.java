package com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.MilkType;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.diary.android.dudhwala.viewmodel.livedatamanager.IMilkTransactionLiveDataManager;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class MilkTransactionLiveDataManagerImpl implements IMilkTransactionLiveDataManager {
    private static final String TAG = "DudhWala/MilkTransactionLiveDataManagerImpl";
    private static final int DEFAULT_MILK_PRICE = 50;
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
    public void insertNewMilkTransaction(MilkTransaction milkTransaction) {
        mRepositoryFactory.getMilkTransactionRepository().insertMilkTransaction(milkTransaction);
    }

    @Override
    public void saveCurrentMilkTransactionState(MilkTransaction milkTransaction) {
        mSelectedMilkTransactionLiveData.setValue(milkTransaction);
    }

    @Override
    public void updateMilkType(int milkType, long date, float price, float quantity) {

        //update price also
        price = getPriceOfMilkType(milkType);
        MilkTransaction milkTransaction = new MilkTransaction(
                mCustomerId, quantity, milkType, price, price * quantity, date, System.currentTimeMillis());
        mSelectedMilkTransactionLiveData.setValue(milkTransaction);
    }

    @Override
    public void removeItemAtPosition(int position) {
        Log.d(TAG, "removeItemAtPosition() position : " + position);
        Optional.ofNullable(mTransactionsArrayListLiveData)
                .ifPresent(listMediatorLiveData -> {

                    listMediatorLiveData.removeSource(source);
                    Optional.ofNullable(source)
                            .map(LiveData::getValue)
                            .ifPresent(milkTransactions -> milkTransactions.remove(position));
                    if (source != null) {
                        listMediatorLiveData.addSource(source, value -> {
                            updateSummery(value);
                            listMediatorLiveData.setValue(value);
                        });
                    }
                });

    }

    @Override
    public void addItemAtPosition(int position, MilkTransaction milkTransaction) {
        Log.d(TAG, "addItemAtPosition() position : " + position);
        Optional.ofNullable(mTransactionsArrayListLiveData)
                .ifPresent(listMediatorLiveData -> {

                    listMediatorLiveData.removeSource(source);
                    Optional.ofNullable(source)
                            .map(LiveData::getValue)
                            .ifPresent(milkTransactions -> milkTransactions.add(position, milkTransaction));
                    if (source != null) {
                        listMediatorLiveData.addSource(source, value -> {
                            updateSummery(value);
                            listMediatorLiveData.setValue(value);
                        });
                    }
                });
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
    public void updateTransactionId(int transactionId) {
        if (transactionId == Constants.MilkTransactionConstants.UNKNOWN_TRANSACTION_ID) {
            mSelectedMilkTransactionLiveData.setValue(getDefaultMilkTransaction());
        } else {
            mSelectedMilkTransactionLiveData.setValue(getMTForTransactionId(transactionId));
        }
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

    private MilkTransaction getDefaultMilkTransaction() {
        CustomerInfo customerInfo = mCustomerInfoLiveData.getValue();
        float pricePerLiter = getPriceOfDefaultMilkType(customerInfo);
        float transactionAmount = customerInfo.getQuickAddMilkType() * pricePerLiter;
        long transactionDate = System.currentTimeMillis();

        MilkTransaction milkTransaction = new MilkTransaction(
                customerInfo.getId(),
                customerInfo.getQuickAddQuantity(),
                customerInfo.getQuickAddMilkType(),
                pricePerLiter,
                transactionAmount,
                transactionDate,
                System.currentTimeMillis());

        return milkTransaction;
    }

    private float getPriceOfDefaultMilkType(CustomerInfo customerInfo) {

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

        mSummeryLiveData.setValue(summeryData);
    }

    private float getPriceOfMilkType(int milkType) {
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
}
