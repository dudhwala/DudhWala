package com.diary.android.dudhwala.view.transaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.common.TimeUtils;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.IActivityActionListener.IDialogFragmentActionListener;
import com.diary.android.dudhwala.view.ILiveDataObserver;
import com.diary.android.dudhwala.view.R;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.MilkTransactionLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionViewActionListener;

import java.util.Calendar;

public class MilkTransactionDialogView implements ILiveDataObserver.MillTransactionLiveDataObserver,
        IDialogFragmentActionListener {

    private final static String TAG = "DudhWala/MilkTransactionDialogView";
    private final Context mContext;
    private final Button mPositiveButton;
    private final Button mNegativeButton;
    private final LinearLayout mDatePickerLayout;

    private MilkTransactionViewActionListener mListener;
    private TextView mDateTextView;
    private EditText mMilkQuantityEditText;
    private EditText mMilkPriceEditText;
    private Spinner mMilkTypeSpinner;
    private LifecycleOwner mLifecycleOwner;
    private MilkTransaction mMilkTransaction;

    public MilkTransactionDialogView(Context context, LifecycleOwner lifecycleOwner, AlertDialog dialog) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;

        mMilkQuantityEditText = dialog.findViewById(R.id.milkQuantity);
        mMilkPriceEditText = dialog.findViewById(R.id.milkPrice);
        mMilkTypeSpinner = dialog.findViewById(R.id.milkTypeSpinner);
        mMilkTypeSpinner.setOnItemSelectedListener(getSpinnerItemSelectedListener());
        mPositiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        mNegativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        mPositiveButton.setOnClickListener(v -> createMilkTransactionAndInsert(dialog));
        mDatePickerLayout = dialog.findViewById(R.id.datePicker);
        mDatePickerLayout.setOnClickListener(getOnClickDateListener());
        mDateTextView = dialog.findViewById(R.id.date);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.v(TAG, "onSaveInstanceState() pos : " + mMilkTypeSpinner.getSelectedItemPosition());
        mMilkTransaction.setTransactionDate(TimeUtils.convertStringToTimestamp(mDateTextView.getText().toString()));
        mMilkTransaction.setPricePerLiter(Float.parseFloat(mMilkPriceEditText.getText().toString()));
        mMilkTransaction.setMilkQuantityLiters(Float.parseFloat(mMilkQuantityEditText.getText().toString()));
        mMilkTransaction.setMilkType(mMilkTypeSpinner.getSelectedItemPosition() + 1);
        mListener.saveCurrentMilkTransactionState(mMilkTransaction);
    }

    private void createMilkTransactionAndInsert(AlertDialog dialog) {

        String quantity = mMilkQuantityEditText.getText().toString();
        String milkPrice = mMilkPriceEditText.getText().toString();
        String date = mDateTextView.getText().toString();
        int milkType = mMilkTypeSpinner.getSelectedItemPosition() + 1;

        if (TextUtils.isEmpty(quantity) || TextUtils.isEmpty(milkPrice) || TextUtils.isEmpty(date)) {
            Toast.makeText(mContext, R.string.enter_all_details_toast, Toast.LENGTH_SHORT).show();
            return;
        }

        mMilkTransaction.setMilkQuantityLiters(Float.parseFloat(quantity));
        mMilkTransaction.setMilkType(milkType);
        mMilkTransaction.setPricePerLiter(Float.parseFloat(milkPrice));
        mMilkTransaction.setTransactionAmount(Float.parseFloat(quantity) * Float.parseFloat(milkPrice));
        mMilkTransaction.setTransactionDate(TimeUtils.convertStringToTimestamp(date));
        mMilkTransaction.setCreatedTimeStamp(System.currentTimeMillis());

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
        milkTransactionLiveData.observe(mLifecycleOwner, this::dataChangedUpdateDialogInfo);
    }

    private void dataChangedUpdateDialogInfo(MilkTransaction milkTransaction) {
        Log.d(TAG, "dataChangedUpdateDialogInfo()");
        mMilkTransaction = milkTransaction;

        mDateTextView.setText(String.valueOf(TimeUtils.convertTimestampToDateString(mMilkTransaction.getTransactionDate())));
        mMilkQuantityEditText.setText((String.valueOf(mMilkTransaction.getMilkQuantityLiters())));
        mMilkTypeSpinner.setSelection(mMilkTransaction.getMilkType() - 1);
        mMilkPriceEditText.setText(String.valueOf(mMilkTransaction.getPricePerLiter()));
    }

    private AdapterView.OnItemSelectedListener getSpinnerItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                //todo

                mListener.updateMilkType(pos + 1,
                        TimeUtils.convertStringToTimestamp(mDateTextView.getText().toString()),
                        Float.parseFloat(mMilkPriceEditText.getText().toString()),
                        Float.parseFloat(mMilkQuantityEditText.getText().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }

    private View.OnClickListener getOnClickDateListener() {
        return v -> {

            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        mDateTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        };
    }
}