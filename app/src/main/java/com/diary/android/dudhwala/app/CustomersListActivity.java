package com.diary.android.dudhwala.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.viewmodel.ICustomerListViewModel;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.CustomerListViewModelImpl;

public class CustomersListActivity extends BaseActivity {

    private static final String TAG = "DudhWala/CustomersListActivity";

    private ICustomerListViewModel mCustomerListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.customers_list_activity);

        configureToolbar();
        createViewModelAndInjectRepositoryFactory();
        injectView();

        findViewById(R.id.fab_add_new_customer).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditCustomerActivity.class);
            intent.putExtra(Constants.Extra.EXTRA_CUSTOMER_ID, -1);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    private ICustomerListViewModel getViewModel() {
        return ViewModelProviders.of(this).get(CustomerListViewModelImpl.class);
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Set Toolbar
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customers_list_activity_menus, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        // Configure the search info and add any event listeners...
        SearchView searchView = (SearchView) searchItem.getActionView();

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                // User chose the "Search" item
                return true;

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.test_activity_launcher:
                launchTestActivity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void launchTestActivity() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);

    }

    @Override
    void createViewModelAndInjectRepositoryFactory() {

        mCustomerListViewModel = getViewModel();
        if (mCustomerListViewModel.isNewInstance()) {
            mCustomerListViewModel.markAsOldInstance();
            mCustomerListViewModel.injectRepositoryFactory(App.getInstance().getRepositoryFactory());
            mCustomerListViewModel.injectLiveDataManager();
        }
    }

    @Override
    void injectView() {
        ViewFactory viewFactory = ViewFactory.getViewFactoryInstance();

        viewFactory.provideCustomerListView(this,
                this,
                findViewById(R.id.recyclerView_customer_list))
                .startObservingLiveData(mCustomerListViewModel, mCustomerListViewModel);

    }
}
