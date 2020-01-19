package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

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

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

// not creating any LiveDataManager in this as we will keep all its livedata in viewmodel only;
public class MilkTransactionDFViewModelImpl extends ViewModel implements MilkTransactionDFViewModel {

    private final String TAG = _TAG + "MilkTransactionDFViewModelImpl";
    private boolean mIsNewInstance = true;

    private MediatorLiveData<MilkTransaction> mSelectedMilkTransactionLiveData = new MediatorLiveData<>();
    private LiveData<CustomerInfo> mCustomerInfoLiveData;

    private ICustomerInfoDataSource mCustomerInfoDataSource;
    private IMilkTransactionDataSource mMilkTransactionDataSource;

    private static final float DEFAULT_MILK_PRICE = 50;
    private int mMilkTransactionId = -1;
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
        mMilkTransactionId = id;
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
        mCustomerInfoLiveData = mCustomerInfoDataSource.getCustomerInfo(mCustomerId);

        if (mMilkTransactionId == Constants.MilkTransactionConstants.UNKNOWN_TRANSACTION_ID) {
            mSelectedMilkTransactionLiveData.addSource(mCustomerInfoLiveData,
                    value -> mSelectedMilkTransactionLiveData.setValue(getDefaultMilkTransaction()));
        } else {
            //just add customer info live data so that it gets updated.
            mSelectedMilkTransactionLiveData.addSource(mCustomerInfoLiveData, v -> {
            });

            mSelectedMilkTransactionLiveData.addSource(
                    mMilkTransactionDataSource.getMilkTransactionForId(mMilkTransactionId),
                    value -> mSelectedMilkTransactionLiveData.setValue(value));
        }

        //todo check about removing resource from mediatorLD
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
    public void updateMilkType(MilkTransaction milkTransaction) {
        //update price also
        milkTransaction.setPricePerLiter(getPriceOfMilkType(milkTransaction.getMilkType()));
        mSelectedMilkTransactionLiveData.setValue(milkTransaction);
    }

    private float getPriceOfMilkType(int milkType) {
        return Optional.ofNullable(mCustomerInfoLiveData)
                .map(LiveData::getValue)
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
        return Optional.ofNullable(mCustomerInfoLiveData)
                .map(LiveData::getValue)
                .map(customerInfo -> {
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
                }).orElse(null);
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
