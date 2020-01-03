package com.diary.android.dudhwala.viewmodelimpl.executor;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.MilkType;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.model.ModelFactory;
import com.diary.android.dudhwala.model.customer.CustomerInfoDataSource;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;
import com.diary.android.dudhwala.viewmodel.executor.AddEditCustomerExecutor;

import java.util.Optional;

public class AddEditCustomerExecutorImpl implements AddEditCustomerExecutor {

    private ModelFactory mModelFactory;

    @Nullable
    private LiveData<CustomerInfo> mCustomerInfoLiveData;

    private CustomerInfoDataSource mCustomerInfoDataSource;

    private int mCustomerId;

    public AddEditCustomerExecutorImpl(ModelFactory modelFactory, int customerId) {

        mModelFactory = modelFactory;
        mCustomerId = customerId;

        mCustomerInfoDataSource = mModelFactory.getCustomerInfoRepository();

        if (mCustomerId != Constants.Customer.UNKNOWN_CUSTOMER_ID) {
            mCustomerInfoLiveData = mCustomerInfoDataSource.getCustomerInfo(mCustomerId);
        }
    }

    @Override
    public LiveData<CustomerInfo> getCustomerInfoLiveData() {
        return mCustomerInfoLiveData;
    }

    @Override
    public void executeUpdateCustomerData(CustomerData customerData) {


        if (mCustomerId == Constants.Customer.UNKNOWN_CUSTOMER_ID) { //adding new
            mCustomerInfoDataSource.addCustomerInfo(makeCustomerInfoForAdd(customerData));
        } else { // updating previous info of customer
            CustomerInfo customerInfo = makeCustomerInfoForEdit(customerData);
            if (customerInfo != null) {
                mCustomerInfoDataSource.editCustomerInfo(customerInfo);
            } else {
                //TODO show toast if data was same.
            }
        }
    }

    private CustomerInfo makeCustomerInfoForEdit(CustomerData customerData) {

        CustomerInfo currentInfo = null;
        if (mCustomerInfoLiveData != null) {
            currentInfo = mCustomerInfoLiveData.getValue();
        }
        CustomerInfo customerInfo = null;

        if (currentInfo != null) {
            customerInfo = new CustomerInfo(customerData.getName(),
                    customerData.getNumber(),
                    customerData.getEmail(),
                    customerData.getAddress(),
                    customerData.getMilkType() == MilkType.COW.intValue() ? customerData.getRate() + 1 : currentInfo.getPricePerLiterCow(),
                    customerData.getMilkType() == MilkType.BUFF.intValue() ? customerData.getRate() : currentInfo.getPricePerLiterBuffalo(),
                    customerData.getMilkType() == MilkType.MIX.intValue() ? customerData.getRate() : currentInfo.getPricePerLiterMix(),
                    customerData.getMilkType(),
                    currentInfo.getQuickAddQuantity(),
                    currentInfo.getTotalAmountDue(),
                    System.currentTimeMillis()
            );
            customerInfo.setId(mCustomerId);
        }

        return customerInfo;

    }

    private CustomerInfo makeCustomerInfoForAdd(CustomerData customerData) {

        return new CustomerInfo(customerData.getName(),
                customerData.getNumber(),
                customerData.getEmail(),
                customerData.getAddress(),
                customerData.getMilkType() == MilkType.COW.intValue() ? customerData.getRate() : Constants.Customer.PRICE_UNKNOWN,
                customerData.getMilkType() == MilkType.BUFF.intValue() ? customerData.getRate() : Constants.Customer.PRICE_UNKNOWN,
                customerData.getMilkType() == MilkType.MIX.intValue() ? customerData.getRate() : Constants.Customer.PRICE_UNKNOWN,
                customerData.getMilkType(),
                Constants.Customer.DEFAULT_QUICK_ADD_QUANTITY,
                0,
                System.currentTimeMillis()
        );
    }


    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

}
