package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.viewmodel.IMilkTransactionViewModel;
import com.diary.android.dudhwala.viewmodel.data.SummeryData;
import com.diary.android.dudhwala.viewmodel.livedatamanager.IMilkTransactionLiveDataManager.DialogLiveDataManager;
import com.diary.android.dudhwala.viewmodel.livedatamanager.IMilkTransactionLiveDataManager.SummeryLiveDataManager;
import com.diary.android.dudhwala.viewmodel.livedatamanager.IMilkTransactionLiveDataManager.TransactionsListLiveDataManager;
import com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl.MilkTransactionLiveDataManagerImpl;

import java.util.List;
import java.util.Optional;

public class MilkTransactionViewModelImpl extends ViewModel implements
        IMilkTransactionViewModel {

    private static final String TAG = "DudhWala/MilkTransactionViewModelImpl";
    private IRepositoryFactory mRepositoryFactory;
    private TransactionsListLiveDataManager mTransactionsListLiveDataManager;
    private DialogLiveDataManager mDialogLiveDataManager;
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
        mDialogLiveDataManager = milkTransactionLiveDataManager;
        mSummeryLiveDataManager = milkTransactionLiveDataManager;

    }

    @Override
    public void setCustomerId(int customerId) {
        mCustomerId = customerId;
    }

    @Override
    public void setTransactionId(int transactionId) {
        mTransactionsListLiveDataManager.updateTransactionId(transactionId);
    }

    @Override
    public void injectRepositoryFactory(IRepositoryFactory repositoryFactory) {
        Log.d(TAG, "injectRepositoryFactory()");
        mRepositoryFactory = repositoryFactory;
    }

    @Override
    public void onClickAddNewMilkTransaction(MilkTransaction newMilkTransaction) {
        mDialogLiveDataManager.insertNewMilkTransaction(newMilkTransaction);
    }

    @Override
    public void onClickEditButton() {

    }

    @Override
    public void onClickDelete(MilkTransaction milkTransaction) {
        mTransactionsListLiveDataManager.deleteMilkTransaction(milkTransaction);

    }

    @Override
    public void saveCurrentMilkTransactionState(MilkTransaction milkTransaction) {
        mDialogLiveDataManager.saveCurrentMilkTransactionState(milkTransaction);
    }

    @Override
    public void updateMilkType(int milkType, long date, int price, int quantity) {
        mDialogLiveDataManager.updateMilkType(milkType, date, price, quantity);
    }

    @Override
    public void onDurationChange(Constants.DurationDirection direction) {
        Log.d(TAG, "onDurationChange()  direction : " + direction);

        //TODO calculate timestamp
        long fromTimestamp = 0;
        long toTimestamp = System.currentTimeMillis();
        mTransactionsListLiveDataManager.updateMilkTransactionDuration(fromTimestamp, toTimestamp);
    }

    @Override
    public Optional<LiveData<List<MilkTransaction>>> provideMilkTransactionListLiveData() {
        return Optional.ofNullable(mTransactionsListLiveDataManager.getTransactionsArrayListLiveData());
    }

    @Override
    public Optional<LiveData<MilkTransaction>> provideSelectedMilkTransactionLiveData() {
        return Optional.ofNullable(mTransactionsListLiveDataManager.getSelectedMilkTransaction());
    }

    @Override
    public Optional<LiveData<SummeryData>> provideMilkTransactionSummeryLiveData() {
        return Optional.ofNullable(mSummeryLiveDataManager.getSummeryLiveData());
    }

    @Override
    public Optional<LiveData<CustomerInfo>> provideCustomerInfoLiveData() {
        return Optional.ofNullable(mSummeryLiveDataManager.getCustomerInfoLiveData());
    }
}
