package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.MilkTransactionViewModelImpl;

import java.util.Optional;

public class MilkTransactionsActivity extends BaseActivity {

    private static final String TAG = "DudhWala/MilkTransactionsActivity";
    private int mCustomerId = Constants.Customer.UNKNOWN_CUSTOMER_ID;
    private ViewFactory mViewFactory;
    private MilkTransactionViewModelImpl mMilkTransactionsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.milk_transactions_activity);
        mCustomerId = Optional.ofNullable(getIntent())
                .map(Intent::getExtras)
                .map(extras -> extras.getInt(Constants.Extra.EXTRA_CUSTOMER_ID))
                .orElse(Constants.Customer.UNKNOWN_CUSTOMER_ID);

        createViewModelAndInjectRepositoryFactory();
        injectView();
    }

    @Override
    void createViewModelAndInjectRepositoryFactory() {
        Log.d(TAG, "createViewModelAndInjectRepositoryFactory()");
        mMilkTransactionsViewModel = getMilkTransactionViewModel();

        if (mMilkTransactionsViewModel.isNewInstance()) {
            mMilkTransactionsViewModel.markAsOldInstance();
            mMilkTransactionsViewModel.injectRepositoryFactory(App.getInstance().getRepositoryFactory());
            mMilkTransactionsViewModel.injectLiveDataManager();
        }
    }

    @Override
    void injectView() {
        Log.d(TAG, "injectView()");
        mViewFactory = ViewFactory.getViewFactoryInstance();

        mViewFactory.provideMilkTransactionListView(this, this, findViewById(R.id.recyclerView))
                .startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);

        mViewFactory.provideMilkTransactionSummeryView(this, this)
                .startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);

        mViewFactory.provideMilkTransactionDurationView(this, this, findViewById(R.id.durationContainer))
                .startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);
    }

    private MilkTransactionViewModelImpl getMilkTransactionViewModel() {
        return ViewModelProviders.of(this).get(MilkTransactionViewModelImpl.class);
    }
}
