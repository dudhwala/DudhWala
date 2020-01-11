package com.diary.android.dudhwala.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.view.IActivityActionListener;
import com.diary.android.dudhwala.view.ViewFactory;
import com.diary.android.dudhwala.view.transaction.MilkTransactionDialogView;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.MilkTransactionViewModelImpl;

public class MilkTransactionDialogFragment extends DialogFragment {

    private final static String TAG = "DudhWala/IDialogFragmentActionListener";
    private MilkTransactionViewModelImpl mMilkTransactionsViewModel;
    private Context mContext;
    private IActivityActionListener.IDialogFragmentActionListener mIDialogFragmentActionListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mMilkTransactionsViewModel = ViewModelProviders.of(((AppCompatActivity) context)).get(MilkTransactionViewModelImpl.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewFactory viewFactory = ViewFactory.getViewFactoryInstance();
        MilkTransactionDialogView mMilkTransactionDialogView = viewFactory.provideMilkTransactionDialogView(mContext, ((LifecycleOwner) mContext), (AlertDialog) getDialog());
        mMilkTransactionDialogView.startObservingLiveData(mMilkTransactionsViewModel, mMilkTransactionsViewModel);

        mIDialogFragmentActionListener = mMilkTransactionDialogView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog()");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_new_milk_transaction, null))
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.cancel, (dialog, whichButton) -> dialog.dismiss());
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mIDialogFragmentActionListener.onSaveInstanceState(outState);
    }
}
