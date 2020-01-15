package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.AddEditCustomerViewImpl;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.viewmodel.IAddEditCustomerViewModel;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.AddEditCustomerViewModelImpl;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Optional;

public class AddEditCustomerActivity extends BaseActivity {

    private static final String TAG = "DudhWala/AddEditCustomerActivity";
    private IAddEditCustomerViewModel mAddEditCustomerViewModel;

    private ViewFactory mViewFactory;

    private int mCustomerId = Constants.Customer.UNKNOWN_CUSTOMER_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_add_edit_customer);

        mCustomerId = Optional.ofNullable(getIntent())
                .map(Intent::getExtras)
                .map(extras -> extras.getInt(Constants.Extra.EXTRA_CUSTOMER_ID))
                .orElse(Constants.Customer.UNKNOWN_CUSTOMER_ID);

        updateActionBar();

        createViewModelAndInjectRepositoryFactory();
        injectView();

    }

    private void updateActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        setSupportActionBar(toolbar);
        if (mCustomerId == Constants.Customer.UNKNOWN_CUSTOMER_ID) {
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.add_new_customer_action_bar_title));
        } else {
            collapsingToolbarLayout.setTitle(getResources().getString(R.string.update_customer_action_bar_title));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private IAddEditCustomerViewModel getAddEditCustomerViewModel() {
        return ViewModelProviders.of(this).get(AddEditCustomerViewModelImpl.class);
    }


    @Override
    void createViewModelAndInjectRepositoryFactory() {
        Log.d(TAG, "createViewModelAndInjectRepositoryFactory()");
        mAddEditCustomerViewModel = getAddEditCustomerViewModel();

        if (mAddEditCustomerViewModel.isNewInstance()) {
            mAddEditCustomerViewModel.markAsOldInstance();
            mAddEditCustomerViewModel.setCustomerId(mCustomerId);
            mAddEditCustomerViewModel.injectRepositoryFactory(App.getInstance().getRepositoryFactory());
            mAddEditCustomerViewModel.injectLiveDataManager();
        }
    }

    @Override
    void injectView() {
        mViewFactory = ViewFactory.getViewFactoryInstance();

        AddEditCustomerViewImpl addEditCustomerView = mViewFactory
                .provideAddEditCustomerView(findViewById(R.id.add_edit_main_view), this, this);
        addEditCustomerView.startObservingLiveData(mAddEditCustomerViewModel,
                mAddEditCustomerViewModel);
    }
}
