package com.diary.android.dudhwala.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.common.MilkType;
import com.diary.android.dudhwala.common.Utils;
import com.diary.android.dudhwala.common.entity.CustomerInfo;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.AddEditLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.AddEditViewActionListner;
import com.diary.android.dudhwala.viewmodel.data.CustomerData;

public class AddEditCustomerViewImpl implements LiveDataObserver.AddEditLiveDataObserver, View.OnClickListener {

    private final String TAG = " AddEditCustomerViewImpl : ";

    @NonNull
    private Context mContext;
    @NonNull
    private LifecycleOwner mLifecycleOwner;
    @NonNull
    private View mView;

    private AddEditViewActionListner mViewActionListener;
    private CustomerInfo mCustomerInfo;
    private int mCustomerInfoMilkType = -1;
    private int mCustomerInfoMiltRate = -1;

    private EditText mName,
            mNumber,
            mEmail,
            mAddress,
            mRate;
    private Spinner mMilktype;
    private Button mAddUpdateButton;


    AddEditCustomerViewImpl(@NonNull View view, @NonNull Context context, @NonNull LifecycleOwner lifecycleOwner) {

        mView = view;
        mContext = context;
        mLifecycleOwner = lifecycleOwner;

        mName = view.findViewById(R.id.et_name);
        mNumber = view.findViewById(R.id.et_number);
        mEmail = view.findViewById(R.id.et_email);
        mAddress = view.findViewById(R.id.et_address);
        mMilktype = view.findViewById(R.id.spn_milktype);
        mRate = view.findViewById(R.id.et_rate);

        mAddUpdateButton = view.findViewById(R.id.btn_Add);
        mAddUpdateButton.setOnClickListener(this);

        mMilktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (mCustomerInfo != null) {
                    mRate.setText(getRateString(pos));
                }
            }

            private String getRateString(int pos) {
                switch (pos) {
                    case 0:
                        return mCustomerInfo.getPricePerLiterCow() + "";
                    case 1:
                        return mCustomerInfo.getPricePerLiterBuffalo() + "";
                    case 2:
                        return mCustomerInfo.getPricePerLiterMix() + "";
                }
                return Constants.Customer.PRICE_UNKNOWN + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void startObservingLiveData(AddEditLiveDataSource liveDataSource, AddEditViewActionListner viewActionListener) {
        mViewActionListener = viewActionListener;
        liveDataSource.provideCustomerInfoLiveData().ifPresent(this::setCustomerInfoLiveData);

    }

    private void setCustomerInfoLiveData(LiveData<CustomerInfo> customerInfoLiveData) {
        customerInfoLiveData.observe(mLifecycleOwner,
                customerInfo -> {
                    if (customerInfo == null) {
                        return;
                    }
                    mCustomerInfo = customerInfo;
                    assignCustomerInfo();
                });
    }

    private void assignCustomerInfo() {
        mName.setText(mCustomerInfo.getCustomerName());
        mNumber.setText(mCustomerInfo.getMobileNumber());
        mEmail.setText(mCustomerInfo.getEmailAddress());
        mAddress.setText(mCustomerInfo.getAddress());
        if (mCustomerInfo.getPricePerLiterCow() > Constants.Customer.PRICE_UNKNOWN) {
            mCustomerInfoMilkType = MilkType.COW.intValue();
            mCustomerInfoMiltRate = mCustomerInfo.getPricePerLiterCow();
        } else if (mCustomerInfo.getPricePerLiterBuffalo() > Constants.Customer.PRICE_UNKNOWN) {
            mCustomerInfoMilkType = MilkType.BUFF.intValue();
            mCustomerInfoMiltRate = mCustomerInfo.getPricePerLiterBuffalo();
        } else if (mCustomerInfo.getPricePerLiterMix() > Constants.Customer.PRICE_UNKNOWN) {
            mCustomerInfoMilkType = MilkType.MIX.intValue();
            mCustomerInfoMiltRate = mCustomerInfo.getPricePerLiterMix();
        }
        mMilktype.setSelection(mCustomerInfoMilkType - 1);
        mRate.setText(Integer.toString(mCustomerInfoMiltRate));

        mAddUpdateButton.setText("Update");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mAddUpdateButton.getId()) {
            CustomerData customerData = makeCustomerData();
            if (customerData == null) {
                Toast.makeText(mContext, "Please Check Info Entered", Toast.LENGTH_LONG).show(); //TOCHECK //String
            } else {
                mViewActionListener.onAddCustomerClicked(customerData);
                Toast.makeText(mContext, "Customer Added", Toast.LENGTH_LONG).show();
                ((AppCompatActivity) mContext).finish();
            }
        }
    }

    // return customer info if its valid else return null
    private CustomerData makeCustomerData() {
        CustomerData customerData = null;

        Log.d(TAG, "makeCustomerData : name " + mName.getText()
                + " rate - " + mRate.getText()
                + " milktype - " + mMilktype.getSelectedItemPosition()
                + " mail - " + mEmail.getText()
                + " address - " + mAddress.getText()
                + " number - " + mNumber.getText());

        if (Utils.isStringNotEmpty(mName.getText().toString())
                && Utils.isStringNotEmpty(mRate.getText().toString())
                && mMilktype.getSelectedItemPosition() >= 0) {

            customerData = new CustomerData(mName.getText().toString(),
                    mNumber.getText().toString(),
                    mEmail.getText().toString(),
                    mAddress.getText().toString(),
                    mMilktype.getSelectedItemPosition() + 1,
                    Integer.parseInt(mRate.getText().toString())
            );
        }

        return customerData;
    }
}
