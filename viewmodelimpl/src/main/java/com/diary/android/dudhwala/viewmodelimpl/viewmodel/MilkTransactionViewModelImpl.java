package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.CustomerInfoForMTActivity;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.RepositoryFactory;
import com.diary.android.dudhwala.viewmodel.MilkTransactionViewModel;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager.DetailDialogLiveDataManager;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager.SummeryLiveDataManager;
import com.diary.android.dudhwala.viewmodel.executor.MilkTransactionLiveDataManager.TransactionsListLiveDataManager;
import com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl.MilkTransactionLiveDataManagerImpl;

import java.util.List;
import java.util.Optional;

public class MilkTransactionViewModelImpl extends ViewModel implements
        MilkTransactionViewModel {

    private static final String TAG = "DudhWala/MilkTransactionViewModelImpl";
    private RepositoryFactory mRepositoryFactory;
    private TransactionsListLiveDataManager mTransactionsListLiveDataManager;
    private DetailDialogLiveDataManager mDetailDialogLiveDataManager;
    private SummeryLiveDataManager mSummeryLiveDataManager;
    private boolean mIsNewInstance = true;
    private int mCustomerId = Constants.Customer.UNKNOWN_CUSTOMER_ID;

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
        MilkTransactionLiveDataManagerImpl milkTransactionLiveDataManager =
                new MilkTransactionLiveDataManagerImpl(mRepositoryFactory, mCustomerId);

        mTransactionsListLiveDataManager = milkTransactionLiveDataManager;
        mDetailDialogLiveDataManager = milkTransactionLiveDataManager;
        mSummeryLiveDataManager = milkTransactionLiveDataManager;

    }

    @Override
    public void setCustomerId(int customerId) {
        mCustomerId = customerId;
    }

    @Override
    public void injectRepositoryFactory(RepositoryFactory repositoryFactory) {
        Log.d(TAG, "injectRepositoryFactory()");
        mRepositoryFactory = repositoryFactory;
    }

    @Override
    public void onListItemClicked(MilkTransaction milkTransaction) {
    }

    @Override
    public void onClickDialogPositiveButton(MilkTransaction newMilkTransaction) {

    }

    @Override
    public void onClickEditButton() {

    }

    @Override
    public void onClickDeleteButton() {

    }

    @Override
    public void onClickAddNewTransactionFab() {

    }

    @Override
    public void onDurationChange(long fromTimeStamp, long toTimestamp) {

    }

    @Override
    public void onClickChangeDuration(Constants.DurationDirection direction) {
        Log.d(TAG, "onClickChangeDuration()  direction : " + direction);

        //TODO calculate timestamp
        long fromTimestamp = 0;
        long toTimestamp = System.currentTimeMillis();
        mTransactionsListLiveDataManager.updateMilkTransactionDuration(fromTimestamp, toTimestamp);
    }

    @Override
    public Optional<LiveData<List<MilkTransaction>>> provideMilkTransactionLiveData() {
        return Optional.ofNullable(mTransactionsListLiveDataManager.getTransactionsArrayListLiveData());
    }

    @Override
    public Optional<LiveData<SummeryData>> provideMilkTransactionSummeryLiveData() {
        return Optional.ofNullable(mSummeryLiveDataManager.getSummeryLiveData());
    }

    @Override
    public Optional<LiveData<Boolean>> provideDialogVisibilityControllerLiveData() {
        return Optional.empty();
    }

    @Override
    public Optional<LiveData<MilkTransaction>> provideDialogMilkTransactionLiveData() {
        return Optional.empty();
    }

    @Override
    public Optional<LiveData<CustomerInfoForMTActivity>> provideCustomerInfoLiveData() {
        return Optional.ofNullable(mSummeryLiveDataManager.getCustomerInfoLiveData());
    }
}
