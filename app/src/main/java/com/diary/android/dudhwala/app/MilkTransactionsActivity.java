package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.CustomCalendarViewModelImpl;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.MilkTransactionViewModelImpl;

import java.util.Optional;

public class MilkTransactionsActivity extends BaseActivity {

    private static final String TAG = "DudhWala/MilkTransactionsActivity";
    private int mCustomerId = Constants.Customer.UNKNOWN_CUSTOMER_ID;
    private MilkTransactionViewModelImpl mMilkTransactionsViewModel;
    private CustomCalendarViewModelImpl mCustomCalendarViewModel;

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

        findViewById(R.id.fab).setOnClickListener(v ->
                showAddNewTransactionDialog(Constants.MilkTransactionConstants.UNKNOWN_TRANSACTION_ID));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
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

        mCustomCalendarViewModel = getCustomCalendarViewModelImpl();
    }

    @Override
    void injectView() {
        Log.d(TAG, "injectView()");
        ViewFactory mViewFactory = ViewFactory.getViewFactoryInstance();

        mViewFactory.provideMilkTransactionSummeryAndToolbarView(this, this,
                findViewById(R.id.summeryViewContainer), findViewById(R.id.toolbar))
                .startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);

        mViewFactory.provideMilkTransactionListView(this, this, findViewById(R.id.transactionListContainer))
                .startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);

        mViewFactory.provideCustomCalendarView(this, this, findViewById(R.id.calendarView))
                .startObservingLiveData(mCustomCalendarViewModel, mCustomCalendarViewModel, mMilkTransactionsViewModel);
    }

    private MilkTransactionViewModelImpl getMilkTransactionViewModel() {
        return ViewModelProviders.of(this).get(MilkTransactionViewModelImpl.class);
    }

    private CustomCalendarViewModelImpl getCustomCalendarViewModelImpl() {
        return ViewModelProviders.of(this).get(CustomCalendarViewModelImpl.class);
    }

    private void showAddNewTransactionDialog(int transactionId) {
        Log.d(TAG, "showAddNewTransactionDialog()");
        mMilkTransactionsViewModel.setTransactionId(transactionId);
        MilkTransactionDialogFragment milkTransactionDialogFragment = new MilkTransactionDialogFragment();
        milkTransactionDialogFragment.show(getSupportFragmentManager(),
                "add_new_milk_transaction_dialog");
    }
}
