package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.model.IRepositoryFactoryLifecycle;
import com.diary.android.dudhwala.viewmodel.IAddEditCustomerViewModel;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;
import com.diary.android.dudhwala.viewmodel.livedatamanager.IAddEditCustomerLiveDataManager;
import com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl.AddEditCustomerLiveDataManagerImpl;

import java.util.Optional;


public class AddEditCustomerViewModelImpl extends ViewModel implements IAddEditCustomerViewModel {

    private boolean isMarked = true;

    private IRepositoryFactory mRepositoryFactory;

    private IAddEditCustomerLiveDataManager mAddEditCustomerLiveDataManager;

    private int mCustomerId = -1;

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepositoryFactory.disconnected(IRepositoryFactoryLifecycle.ViewModelType.addEditCustomerVM);
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
    public void injectRepositoryFactory(IRepositoryFactory repositoryFactory) {
        mRepositoryFactory = repositoryFactory;
        repositoryFactory.connected(IRepositoryFactoryLifecycle.ViewModelType.addEditCustomerVM);
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
