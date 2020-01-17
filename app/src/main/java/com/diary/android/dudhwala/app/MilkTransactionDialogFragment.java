package com.diary.android.dudhwala.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.view.transaction.MilkTransactionDialogView;
import com.diary.android.dudhwala.viewmodel.MilkTransactionDFViewModel;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.MilkTransactionDFViewModelImpl;

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

public class MilkTransactionDialogFragment extends DialogFragment {

    private final static String TAG = _TAG + "IDialogFragmentActionListener";
    private Context mContext;
    private MilkTransactionDFViewModel mMilkTransactionDFViewModel;
    private MilkTransactionDialogView mMilkTransactionDialogView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg == null) {
            //TODO arguments can not be null
        }
        int customerId = arg.getInt(Constants.Customer.CUSTOMER_ID);
        int transactionId = arg.getInt(Constants.MilkTransactionConstants.TRANSACTION_STRING);
        mMilkTransactionDFViewModel = getMilkTransactionDFViewModel();
        if (mMilkTransactionDFViewModel.isNewInstance()) {
            mMilkTransactionDFViewModel.markAsOldInstance();
            mMilkTransactionDFViewModel.setCustomerId(customerId);
            mMilkTransactionDFViewModel.setMilkTransactionId(transactionId);
            mMilkTransactionDFViewModel.injectRepositoryFactory(App.getInstance().getRepositoryFactory());
            mMilkTransactionDFViewModel.injectLiveDataManager();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog()");

        ViewFactory viewFactory = ViewFactory.getViewFactoryInstance();
        mMilkTransactionDialogView = viewFactory.provideMilkTransactionDialogView(mContext, ((LifecycleOwner) mContext));
        mMilkTransactionDialogView.startObservingLiveData(mMilkTransactionDFViewModel, mMilkTransactionDFViewModel);

        return mMilkTransactionDialogView.createDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("nainaa", "onActivityCreated");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMilkTransactionDialogView.onSaveInstanceState(outState);
    }

    private MilkTransactionDFViewModel getMilkTransactionDFViewModel() {
        return ViewModelProviders.of(this).get(MilkTransactionDFViewModelImpl.class);
    }

}
