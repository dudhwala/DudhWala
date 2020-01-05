package com.diary.android.dudhwala.view.transaction;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.IActivityActionListener.IDialogFragmentActionListener;
import com.diary.android.dudhwala.view.LiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.viewmodel.LiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.MilkTransactionViewActionListener;


public class MilkTransactionDialogView implements LiveDataObserver.MillTransactionLiveDataObserver,
        IDialogFragmentActionListener {

    private final static String TAG = "DudhWala/MilkTransactionDialogView";
    private final Context mContext;
    private final Button mPositiveButton;
    private final Button mNegativeButton;

    private MilkTransactionViewActionListener mListener;
    private EditText mDateEditText;
    private EditText mMilkQuantityEditText;
    private EditText mMilkPriceEditText;
    private Spinner mMilkTypeSpinner;
    private LifecycleOwner mLifecycleOwner;
    private MilkTransaction mMilkTransaction;
    private AdapterView.OnItemSelectedListener mSpinnerItemClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
            //TODO
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public MilkTransactionDialogView(Context context, LifecycleOwner lifecycleOwner, AlertDialog dialog) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;

        mDateEditText = dialog.findViewById(R.id.date);
        mMilkQuantityEditText = dialog.findViewById(R.id.milkQuantity);
        mMilkPriceEditText = dialog.findViewById(R.id.milkPrice);
        mMilkTypeSpinner = dialog.findViewById(R.id.milkTypeSpinner);
        mMilkTypeSpinner.setOnItemSelectedListener(mSpinnerItemClickListener);
        mPositiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        mNegativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        mPositiveButton.setOnClickListener(v -> createMilkTransactionAndInsert(dialog));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.v(TAG, "onSaveInstanceState() pos : " + mMilkTypeSpinner.getSelectedItemPosition());
        mMilkTransaction.setTransactionDate(Long.parseLong(mDateEditText.getText().toString()));
        mMilkTransaction.setPricePerLiter(Integer.parseInt(mMilkPriceEditText.getText().toString()));
        mMilkTransaction.setMilkQuantityLiters(Integer.parseInt(mMilkQuantityEditText.getText().toString()));
        mMilkTransaction.setMilkType(mMilkTypeSpinner.getSelectedItemPosition() + 1);
        mListener.saveCurrentMilkTransactionState(mMilkTransaction);
    }

    private void createMilkTransactionAndInsert(AlertDialog dialog) {

        String quantity = mMilkQuantityEditText.getText().toString();
        String milkPrice = mMilkPriceEditText.getText().toString();
        String date = mDateEditText.getText().toString();
        int milkType = mMilkTypeSpinner.getSelectedItemPosition() + 1;

        if (TextUtils.isEmpty(quantity) || TextUtils.isEmpty(milkPrice) || TextUtils.isEmpty(date)) {
            Toast.makeText(mContext, R.string.enter_all_details_toast, Toast.LENGTH_SHORT).show();
            return;
        }

        mMilkTransaction.setMilkQuantityLiters(Integer.parseInt(quantity));
        mMilkTransaction.setMilkType(milkType);
        mMilkTransaction.setPricePerLiter(Integer.parseInt(milkPrice));
        mMilkTransaction.setTransactionAmount(Integer.parseInt(quantity) * Integer.parseInt(milkPrice));
        mMilkTransaction.setTransactionDate(Long.parseLong(date));

        mListener.onClickAddNewMilkTransaction(mMilkTransaction);
        dialog.dismiss();
    }

    @Override
    public void startObservingLiveData(MilkTransactionLiveDataSource liveDataSource,
                                       MilkTransactionViewActionListener viewActionListener) {
        Log.d(TAG, "startObservingLiveData()");
        this.mListener = viewActionListener;
        liveDataSource.provideSelectedMilkTransactionLiveData().ifPresent(this::setMilkTransactionObserver);
    }

    private void setMilkTransactionObserver(LiveData<MilkTransaction> milkTransactionLiveData) {
        milkTransactionLiveData.observe(mLifecycleOwner, milkTransaction -> dataChangedUpdateDialogInfo(milkTransaction));
    }

    private void dataChangedUpdateDialogInfo(MilkTransaction milkTransaction) {
        Log.d(TAG, "dataChangedUpdateDialogInfo()");
        mMilkTransaction = milkTransaction;

        mDateEditText.setText(String.valueOf(mMilkTransaction.getTransactionDate()));
        mMilkQuantityEditText.setText((String.valueOf(mMilkTransaction.getMilkQuantityLiters())));
        mMilkTypeSpinner.setSelection(mMilkTransaction.getMilkType() - 1);
        mMilkPriceEditText.setText(String.valueOf(mMilkTransaction.getPricePerLiter()));
    }
}