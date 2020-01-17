package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.MilkType;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.IMilkTransactionDataSource;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.model.customer.ICustomerInfoDataSource;
import com.diary.android.dudhwala.viewmodel.MilkTransactionDFViewModel;

import java.util.Optional;

public class MilkTransactionDFViewModelImpl extends ViewModel implements MilkTransactionDFViewModel {

    private boolean mIsNewInstance = true;

    private MediatorLiveData<MilkTransaction> mSelectedMilkTransactionLiveData = new MediatorLiveData<>();

    private MediatorLiveData<CustomerInfo> mCustomerInfoLiveData = new MediatorLiveData<>();

    private ICustomerInfoDataSource mCustomerInfoDataSource;

    private IMilkTransactionDataSource mMilkTransactionDataSource;

    private static final float DEFAULT_MILK_PRICE = 50;

    private int mMilkTransationId = -1;

    private int mCustomerId = -1;

    @Override
    public boolean isNewInstance() {
        return mIsNewInstance;
    }

    @Override
    public void markAsOldInstance() {
        mIsNewInstance = false;
    }

    @Override
    public void setMilkTransactionId(int id) {
        mMilkTransationId = id;

    }

    @Override
    public void setCustomerId(int id) {
        // TODO should never be <0
        mCustomerId = id;
    }

    @Override
    public void injectRepositoryFactory(IRepositoryFactory repositoryFactory) {

        mCustomerInfoDataSource = repositoryFactory.getCustomerInfoRepository();
        mMilkTransactionDataSource = repositoryFactory.getMilkTransactionRepository();

    }

    @Override
    public void injectLiveDataManager() {
        // not creating any LiveDataManager in this as we will keep all its livedata in viewmodel only;

        mSelectedMilkTransactionLiveData.addSource(mCustomerInfoDataSource.getCustomerInfo(mCustomerId),
                value -> {
                    Log.d("nainaa", "customerInfo : " + value);
                    if (value == null) {
                        return;
                    }
                    mCustomerInfoLiveData.setValue(value);
                    if (mMilkTransationId == Constants.MilkTransactionConstants.UNKNOWN_TRANSACTION_ID) {
                        mSelectedMilkTransactionLiveData.setValue(getDefaultMilkTransaction());
                    } else {
                        mSelectedMilkTransactionLiveData.setValue(getMTForTransactionId());
                    }
                });

        //todo check about removing resource from mediatorLD
        //mMilkTransactionDataSource.getMilkTransactionForId(mMilkTransationId);

    }

    private MilkTransaction getMTForTransactionId() {
        return null;
    }

    @Override
    public void onClickAddMilkTransaction(MilkTransaction newMilkTransaction) {
        mMilkTransactionDataSource.insertMilkTransaction(newMilkTransaction);

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

    private float getPriceOfMilkType(int milkType) {
        CustomerInfo customerInfo = mCustomerInfoLiveData.getValue();
        return Optional.ofNullable(customerInfo)
                .map(info -> {
                    float price;
                    if (milkType == MilkType.COW.intValue()) {
                        price = info.getPricePerLiterCow();
                    } else if (milkType == MilkType.BUFF.intValue()) {
                        price = info.getPricePerLiterBuffalo();
                    } else {
                        price = info.getPricePerLiterMix();
                    }
                    return price;
                }).orElse(DEFAULT_MILK_PRICE);
    }

    private float getPriceOfDefaultMilkType(CustomerInfo customerInfo) {

        return Optional.ofNullable(customerInfo)
                .map(info -> {
                    float price;
                    if (info.getQuickAddMilkType() == MilkType.COW.intValue()) {
                        price = info.getPricePerLiterCow();
                    } else if (info.getQuickAddMilkType() == MilkType.BUFF.intValue()) {
                        price = info.getPricePerLiterBuffalo();
                    } else {
                        price = info.getPricePerLiterMix();
                    }
                    return price;
                }).orElse(DEFAULT_MILK_PRICE);

    }

    private MilkTransaction getDefaultMilkTransaction() {
        CustomerInfo customerInfo = mCustomerInfoLiveData.getValue();
        float pricePerLiter = getPriceOfDefaultMilkType(customerInfo);
        float transactionAmount = customerInfo.getQuickAddMilkType() * pricePerLiter;
        long transactionDate = System.currentTimeMillis();

        return new MilkTransaction(
                customerInfo.getId(),
                customerInfo.getQuickAddQuantity(),
                customerInfo.getQuickAddMilkType(),
                pricePerLiter,
                transactionAmount,
                transactionDate,
                System.currentTimeMillis());
    }

    @Override
    public Optional<LiveData<MilkTransaction>> provideSelectedMilkTransactionLiveData() {
        return Optional.ofNullable(mSelectedMilkTransactionLiveData);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}