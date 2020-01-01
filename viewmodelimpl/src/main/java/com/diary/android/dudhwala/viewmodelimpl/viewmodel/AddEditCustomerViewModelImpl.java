package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.ModelFactory;
import com.diary.android.dudhwala.model.ModelFactoryLifecycle;
import com.diary.android.dudhwala.viewmodel.AddEditCustomerViewModel;
import com.diary.android.dudhwala.viewmodel.executor.AddEditCustomerExecutor;
import com.diary.android.dudhwala.viewmodelimpl.executor.AddEditCustomerExecutorImpl;

import java.util.Optional;


public class AddEditCustomerViewModelImpl extends ViewModel implements AddEditCustomerViewModel {

    private boolean isMarked = true;

    private ModelFactory mModelFactory;

    private AddEditCustomerExecutor mAddEditCustomerExecutor;

    private int mCustomerId = -1;


    @Override
    protected void onCleared() {
        super.onCleared();
        mModelFactory.disconnected(ModelFactoryLifecycle.ViewModelType.addEditCustomerVM);
    }

    @Override
    public boolean isNewInstance() {
        return isMarked;
    }

    @Override
    public void markThisInstance() {
        isMarked = false;
    }

    @Override
    public void injectModelFactory(ModelFactory modelFactory) {
        mModelFactory = modelFactory;
        modelFactory.connected(ModelFactoryLifecycle.ViewModelType.addEditCustomerVM);
    }

    @Override
    public void setCustomerId(int customerId) {
        mCustomerId = customerId;
    }

    @Override
    public void injectExecutors() {
        mAddEditCustomerExecutor = new AddEditCustomerExecutorImpl(mModelFactory);

    }

    @Override
    public Optional<LiveData<CustomerInfo>> provideCustomerInfoLiveData() {
        return Optional.ofNullable(mAddEditCustomerExecutor.getCustomerInfoLiveData());
    }

    @Override
    public void onAddCustomerClicked(CustomerInfo customerInfo) {

    }

}
