package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.viewmodel.ICustomerListViewModel;
import com.diary.android.dudhwala.viewmodel.livedatamanager.ICustomerListLiveDataManager;
import com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl.CustomerListLiveDataManagerImpl;

import java.util.List;
import java.util.Optional;

public class CustomerListViewModelImpl extends ViewModel implements ICustomerListViewModel {

    private boolean isMarked = true;

    private ICustomerListLiveDataManager mCustomerListLiveDataManager;

    private IRepositoryFactory mRepositoryFactory;

    @Override
    public boolean isNewInstance() {
        return isMarked;
    }

    @Override
    public void markAsOldInstance() {
        isMarked = false;
    }

    @Override
    public void injectRepositoryFactory(IRepositoryFactory repositoryFactory) {
        mRepositoryFactory = repositoryFactory;
    }

    @Override
    public void injectLiveDataManager() {
        mCustomerListLiveDataManager = new CustomerListLiveDataManagerImpl(mRepositoryFactory);
    }

    @Override
    public Optional<LiveData<List<CustomerInfo>>> provideCustomerListLiveData() {
        return Optional.ofNullable(mCustomerListLiveDataManager)
                .map(cl -> cl.provideCustomerListLiveData());
    }

    @Override
    public void onCustomerListItemClicked(CustomerInfo customerInfo) {

    }

    @Override
    public void onQuickAddMilkTransactionClicked() {

    }

    @Override
    public void onAddMilkTransactionClicked() {

    }
}
