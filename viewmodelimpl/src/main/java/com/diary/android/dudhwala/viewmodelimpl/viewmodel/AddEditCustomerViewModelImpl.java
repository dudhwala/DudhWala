package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.RepositoryFactory;
import com.diary.android.dudhwala.model.RepositoryFactoryLifecycle;
import com.diary.android.dudhwala.viewmodel.AddEditCustomerViewModel;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;
import com.diary.android.dudhwala.viewmodel.executor.AddEditCustomerLiveDataManager;
import com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl.AddEditCustomerLiveDataManagerImpl;

import java.util.Optional;


public class AddEditCustomerViewModelImpl extends ViewModel implements AddEditCustomerViewModel {

    private boolean isMarked = true;

    private RepositoryFactory mRepositoryFactory;

    private AddEditCustomerLiveDataManager mAddEditCustomerLiveDataManager;

    private int mCustomerId = -1;

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepositoryFactory.disconnected(RepositoryFactoryLifecycle.ViewModelType.addEditCustomerVM);
    }

    @Override
    public boolean isNewInstance() {
        return isMarked;
    }

    @Override
    public void markAsOldInstance() {
        isMarked = false;
    }

    @Override
    public void setCustomerId(int customerId) {
        mCustomerId = customerId;
    }

    @Override
    public void injectRepositoryFactory(RepositoryFactory repositoryFactory) {
        mRepositoryFactory = repositoryFactory;
        repositoryFactory.connected(RepositoryFactoryLifecycle.ViewModelType.addEditCustomerVM);
    }

    @Override
    public void injectLiveDataManager() {
        mAddEditCustomerLiveDataManager = new AddEditCustomerLiveDataManagerImpl(mRepositoryFactory, mCustomerId);

    }

    @Override
    public Optional<LiveData<CustomerInfo>> provideCustomerInfoLiveData() {
        return Optional.ofNullable(mAddEditCustomerLiveDataManager.getCustomerInfoLiveData());
    }

    @Override
    public void onAddCustomerClicked(CustomerData customerData) {
        mAddEditCustomerLiveDataManager.executeUpdateCustomerData(customerData);
    }
}
