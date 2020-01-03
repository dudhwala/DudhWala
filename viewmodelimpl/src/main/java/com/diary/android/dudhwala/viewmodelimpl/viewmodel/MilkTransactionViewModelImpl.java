package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.model.RepositoryFactory;
import com.diary.android.dudhwala.viewmodel.MilkTransactionViewModel;
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
        mTransactionsListLiveDataManager = new MilkTransactionLiveDataManagerImpl(mRepositoryFactory);
        mDetailDialogLiveDataManager = new MilkTransactionLiveDataManagerImpl(mRepositoryFactory);
        mSummeryLiveDataManager = new MilkTransactionLiveDataManagerImpl(mRepositoryFactory);

    }

    @Override
    public void injectRepositoryFactory(RepositoryFactory repositoryFactory) {
        Log.d(TAG, "injectRepositoryFactory()");
        mRepositoryFactory = repositoryFactory;
    }

    @Override
    public void onListItemClicked(MilkTransaction milkTransaction) {
        onDurationChange(0L, System.currentTimeMillis());
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
        mTransactionsListLiveDataManager.onDurationChanged(fromTimeStamp, toTimestamp);
    }

    @Override
    public void onClickChangeDuration(Constants.DurationDirection direction) {
        Log.d(TAG, "onClickChangeDuration()  direction : " + direction);
        mTransactionsListLiveDataManager.onClickDurationChange(direction);
    }

    @Override
    public Optional<LiveData<List<MilkTransaction>>> provideMilkTransactionLiveData() {
        return Optional.ofNullable(mTransactionsListLiveDataManager.getTransactionsArrayListLiveData());
    }

    @Override
    public Optional<LiveData<MilkTransaction>> provideMilkTransactionSummeryLiveData() {
        return Optional.empty();
    }

    @Override
    public Optional<LiveData<Boolean>> provideDialogVisibilityControllerLiveData() {
        return Optional.empty();
    }

    @Override
    public Optional<LiveData<MilkTransaction>> provideDialogMilkTransactionLiveData() {
        return Optional.empty();
    }
}
