package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.MilkTransactionViewModelImpl;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Optional;

public class MilkTransactionsActivity extends BaseActivity {

    private static final String TAG = "DudhWala/MilkTransactionsActivity";
    private int mCustomerId = Constants.Customer.UNKNOWN_CUSTOMER_ID;
    private MilkTransactionViewModelImpl mMilkTransactionsViewModel;
    private AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.milk_transaction_activity);
        Log.d(TAG, "onCreate() setContentView");

        mCustomerId = Optional.ofNullable(getIntent())
                .map(Intent::getExtras)
                .map(extras -> extras.getInt(Constants.Extra.EXTRA_CUSTOMER_ID))
                .orElse(Constants.Customer.UNKNOWN_CUSTOMER_ID);

        createViewModelAndInjectRepositoryFactory();
        injectView();

        mAppBarLayout = findViewById(R.id.appBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

        updateAppBarExpendedState(getResources().getConfiguration().orientation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.milk_transaction_activity_menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_show_payments:
                // User chose the "Payment" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateAppBarExpendedState(int orientation) {
        Log.d(TAG, "updateAppBarExpendedState() orientation : " + orientation);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mAppBarLayout.setExpanded(false);
        } else {
            mAppBarLayout.setExpanded(true);
        }
    }

    @Override
    void createViewModelAndInjectRepositoryFactory() {
        Log.d(TAG, "createViewModelAndInjectRepositoryFactory()");
        mMilkTransactionsViewModel = getMilkTransactionViewModel();

        if (mMilkTransactionsViewModel.isNewInstance()) {
            mMilkTransactionsViewModel.markAsOldInstance();
            mMilkTransactionsViewModel.setCustomerId(mCustomerId);
            mMilkTransactionsViewModel.injectRepositoryFactory(App.getInstance().getRepositoryFactory());
            mMilkTransactionsViewModel.injectLiveDataManager();

        }
    }

    @Override
    void injectView() {
        Log.d(TAG, "injectView()");
        ViewFactory mViewFactory = ViewFactory.getViewFactoryInstance();

        mViewFactory.provideMilkTransactionSummeryAndToolbarView(this, this,
                findViewById(R.id.summeryViewContainer), findViewById(R.id.collapsingToolbar))
                .startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);

        mViewFactory.provideMilkTransactionListView(this, this,
                findViewById(R.id.transactionListContainer), mCustomerId)
                .startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);

        mViewFactory.provideCustomCalendarView(this, this, findViewById(R.id.calendarView))
                .startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);
    }

    private MilkTransactionViewModelImpl getMilkTransactionViewModel() {
        return ViewModelProviders.of(this).get(MilkTransactionViewModelImpl.class);
    }

    private void showAddEditMilkTransactionDialog(int transactionId, int customerId) {
        Log.d(TAG, "showAddEditMilkTransactionDialog()");
        MilkTransactionDialogFragment milkTransactionDialogFragment = new MilkTransactionDialogFragment();

        Bundle args = new Bundle();
        args.putInt(Constants.Customer.CUSTOMER_ID, customerId);
        args.putInt(Constants.MilkTransactionConstants.TRANSACTION_STRING, transactionId);
        milkTransactionDialogFragment.setArguments(args);

        milkTransactionDialogFragment.show(getSupportFragmentManager(), "add_edit_milk_transaction_dialog");
    }
}
