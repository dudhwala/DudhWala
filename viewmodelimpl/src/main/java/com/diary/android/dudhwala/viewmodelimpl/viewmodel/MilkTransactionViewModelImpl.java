package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.TimeUtils;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.viewmodel.IMilkTransactionViewModel;
import com.diary.android.dudhwala.viewmodel.data.DurationData;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.diary.android.dudhwala.viewmodel.livedatamanager.ICustomCalenderLiveDataManager;
import com.diary.android.dudhwala.viewmodel.livedatamanager.IMilkTransactionLiveDataManager;
import com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl.CustomCalenderLiveDataManagerImpl;
import com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl.MilkTransactionLiveDataManagerImpl;

import java.util.List;
import java.util.Optional;

public class MilkTransactionViewModelImpl extends ViewModel implements IMilkTransactionViewModel {

    private static final String TAG = "DudhWala/MilkTransactionViewModelImpl";
    private IRepositoryFactory mRepositoryFactory;
    private boolean mIsNewInstance = true;
    private int mCustomerId = Constants.Customer.UNKNOWN_CUSTOMER_ID;
    private IMilkTransactionLiveDataManager mMilkTransactionLiveDataManager;
    private ICustomCalenderLiveDataManager mCustomCalenderLiveDataManager;

    @Override
    public boolean isNewInstance() {
        return mIsNewInstance;
    }

    @Override
    public void markAsOldInstance() {
        mIsNewInstance = false;
    }

    @Override
    public void injectLiveDataManager() {
        Log.d(TAG, "injectLiveDataManager()");
        mMilkTransactionLiveDataManager =
                new MilkTransactionLiveDataManagerImpl(mRepositoryFactory, mCustomerId);
        mCustomCalenderLiveDataManager = new CustomCalenderLiveDataManagerImpl();
    }

    @Override
    public void setCustomerId(int customerId) {
        mCustomerId = customerId;
    }

    @Override
    public void setTransactionId(int transactionId) {
        mMilkTransactionLiveDataManager.updateTransactionId(transactionId);
    }

    @Override
    public void injectRepositoryFactory(IRepositoryFactory repositoryFactory) {
        Log.d(TAG, "injectRepositoryFactory()");
        mRepositoryFactory = repositoryFactory;
    }

    @Override
    public void onClickAddMilkTransaction(MilkTransaction newMilkTransaction) {
        mMilkTransactionLiveDataManager.insertMilkTransaction(newMilkTransaction);
    }

    @Override
    public void onClickEditButton() {

    }

    @Override
    public void onClickDelete(MilkTransaction milkTransaction) {
        mMilkTransactionLiveDataManager.deleteMilkTransaction(milkTransaction);

    }

    @Override
    public void saveCurrentMilkTransactionState(MilkTransaction milkTransaction) {
        mMilkTransactionLiveDataManager.saveCurrentMilkTransactionState(milkTransaction);
    }

    @Override
    public void updateMilkType(int milkType, long date, float price, float quantity) {
        mMilkTransactionLiveDataManager.updateMilkType(milkType, date, price, quantity);
    }

    @Override
    public void onDurationChange(int month, int year) {
        //Log.d(TAG, "onDurationChange()  month/year : " + month + "/" + year);

        long fromTimestamp = TimeUtils.getMonthStartTimeStamp(month, year);
        long toTimestamp = TimeUtils.getMonthEndTimeStamp(month, year);
        mMilkTransactionLiveDataManager.updateMilkTransactionDuration(fromTimestamp, toTimestamp);
    }


    @Override
    public void onClickCustomCalenderButton(int button) {
        mCustomCalenderLiveDataManager.onClickCustomCalenderButton(button);

    }

    @Override
    public void initializeCalendar() {
        mCustomCalenderLiveDataManager.initializeCalendar();

    }

    @Override
    public Optional<LiveData<DurationData>> provideDurationLiveData() {
        return Optional.ofNullable(mCustomCalenderLiveDataManager)
                .map(ICustomCalenderLiveDataManager::provideDurationLiveData);
    }

    @Override
    public Optional<LiveData<List<MilkTransaction>>> provideMilkTransactionListLiveData() {
        return Optional.ofNullable(mMilkTransactionLiveDataManager)
                .map(IMilkTransactionLiveDataManager::getTransactionsArrayListLiveData);
    }

    @Override
    public Optional<LiveData<MilkTransaction>> provideSelectedMilkTransactionLiveData() {
        return Optional.ofNullable(mMilkTransactionLiveDataManager)
                .map(IMilkTransactionLiveDataManager::getSelectedMilkTransaction);
    }

    @Override
    public Optional<LiveData<SummeryData>> provideMilkTransactionSummeryLiveData() {
        return Optional.ofNullable(mMilkTransactionLiveDataManager)
                .map(IMilkTransactionLiveDataManager::getSummeryLiveData);
    }

    @Override
    public Optional<LiveData<CustomerInfo>> provideCustomerInfoLiveData() {
        return Optional.ofNullable(mMilkTransactionLiveDataManager)
                .map(IMilkTransactionLiveDataManager::getCustomerInfoLiveData);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
