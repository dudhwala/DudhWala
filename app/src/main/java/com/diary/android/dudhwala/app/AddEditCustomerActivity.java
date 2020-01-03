package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.AddEditCustomerViewImpl;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.viewmodel.AddEditCustomerViewModel;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.AddEditCustomerViewModelImpl;

import java.util.Optional;

public class AddEditCustomerActivity extends AppCompatActivity {

    private AddEditCustomerViewModel mAddEditCustomerViewModel;

    private ViewFactory mViewFactory;

    private int mCustomerId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_customer);

        mCustomerId = Optional.ofNullable(getIntent())
                .map(Intent::getExtras)
                .map(extras -> extras.getInt(Constants.Extra.EXTRA_CUSTOMER_ID))
                .orElse(Constants.Customer.UNKNOWN_CUSTOMER_ID);

        mAddEditCustomerViewModel = getAddEditCustomerViewModel();

        if (mAddEditCustomerViewModel.isNewInstance()) {
            mAddEditCustomerViewModel.markThisInstance();
            mAddEditCustomerViewModel.setCustomerId(mCustomerId); //LEARN https://stackoverflow.com/questions/46283981/android-viewmodel-additional-arguments
            mAddEditCustomerViewModel.injectModelFactory(App.getInstance().getModelFactory());
            mAddEditCustomerViewModel.injectExecutors();
        }

        injectView();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private AddEditCustomerViewModel getAddEditCustomerViewModel() {
        return ViewModelProviders.of(this).get(AddEditCustomerViewModelImpl.class);
    }

    private void injectView() {
        mViewFactory = new ViewFactory();


        AddEditCustomerViewImpl addEditCustomerView = mViewFactory.provideAddEditCustomerView(findViewById(R.id.add_edit_main_view), this, this);
        addEditCustomerView.startObservingLiveData(getAddEditCustomerViewModel(), getAddEditCustomerViewModel());

    }
}
