package com.diary.android.dudhwala.view.transaction;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.TimeUtils;
import com.diary.android.dudhwala.common.entity.MilkTransaction;
import com.diary.android.dudhwala.view.ILiveDataObserver;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.MilkTransactionDFLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionDFViewActionListener;

import java.util.Calendar;
import java.util.Optional;

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

public class MilkTransactionDialogView implements ILiveDataObserver.MillTransactionDFLiveDataObserver {

    private final static String TAG = _TAG + "MilkTransactionDialogView";
    private final Context mContext;
    private LinearLayout mDatePickerLayout;

    private MilkTransactionDFViewActionListener mViewActionListener;
    private TextView mDateTextView;
    private EditText mMilkQuantityEditText;
    private EditText mMilkPriceEditText;
    private Spinner mMilkTypeSpinner;
    private LifecycleOwner mLifecycleOwner;
    private MilkTransaction mMilkTransaction;

    public MilkTransactionDialogView(Context context, LifecycleOwner lifecycleOwner) {
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
    }

    public AlertDialog createDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_add_new_milk_transaction, null);

        mMilkQuantityEditText = view.findViewById(R.id.milkQuantity);
        mMilkPriceEditText = view.findViewById(R.id.milkPrice);
        mMilkTypeSpinner = view.findViewById(R.id.milkTypeSpinner);
        mMilkTypeSpinner.setOnItemSelectedListener(getSpinnerItemSelectedListener());
        mDatePickerLayout = view.findViewById(R.id.datePicker);
        mDatePickerLayout.setOnClickListener(getOnClickDateListener());
        mDateTextView = view.findViewById(R.id.date);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view)
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.cancel, (dialog, whichButton) -> dialog.dismiss());
        return builder.create();
    }

    public void onSaveInstanceState() {
        Log.v(TAG, "onSaveInstanceState() pos : " + mMilkTypeSpinner.getSelectedItemPosition());
        mMilkTransaction.setTransactionDate(TimeUtils.convertStringToTimestamp(mDateTextView.getText().toString()));
        mMilkTransaction.setPricePerLiter(Float.parseFloat(mMilkPriceEditText.getText().toString()));
        mMilkTransaction.setMilkQuantityLiters(Float.parseFloat(mMilkQuantityEditText.getText().toString()));
        mMilkTransaction.setMilkType(mMilkTypeSpinner.getSelectedItemPosition() + 1);
        mViewActionListener.saveCurrentMilkTransactionState(mMilkTransaction);
    }

    private boolean createMilkTransactionAndInsert() {

        String q = mMilkQuantityEditText.getText().toString();
        String milkPrice = mMilkPriceEditText.getText().toString();
        String date = mDateTextView.getText().toString();
        int milkType = mMilkTypeSpinner.getSelectedItemPosition() + 1;

        float quantity = 0;
        float price = 0;
        if (!TextUtils.isEmpty(q)) {
            quantity = Float.parseFloat(q);
        }
        if (!TextUtils.isEmpty(milkPrice)) {
            price = Float.parseFloat(milkPrice);
        }

        if (quantity <= 0 || price <= 0 || TextUtils.isEmpty(date)) {
            Toast.makeText(mContext, R.string.enter_all_details_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.d(TAG, "createMilkTransactionAndInsert() mMilkTransaction : " + mMilkTransaction);

        mMilkTransaction.setMilkQuantityLiters(quantity);
        mMilkTransaction.setMilkType(milkType);
        mMilkTransaction.setPricePerLiter(price);
        mMilkTransaction.setTransactionAmount(quantity * price);
        mMilkTransaction.setTransactionDate(TimeUtils.convertStringToTimestamp(date));
        mMilkTransaction.setCreatedTimeStamp(System.currentTimeMillis());

        Log.d(TAG, "createMilkTransactionAndInsert()1 mMilkTransaction : " + mMilkTransaction);
        mViewActionListener.onClickAddMilkTransaction(mMilkTransaction);
        return true;
    }


    @Override
    public void startObservingLiveData(MilkTransactionDFLiveDataSource liveDataSource,
                                       MilkTransactionDFViewActionListener viewActionListener) {
        Log.d(TAG, "startObservingLiveData()");
        mViewActionListener = viewActionListener;
        liveDataSource.provideSelectedMilkTransactionLiveData().ifPresent(this::setMilkTransactionObserver);
    }

    private void setMilkTransactionObserver(LiveData<MilkTransaction> milkTransactionLiveData) {
        milkTransactionLiveData.observe(mLifecycleOwner, this::dataChangedUpdateDialogInfo);
    }

    private void dataChangedUpdateDialogInfo(MilkTransaction milkTransaction) {
        mMilkTransaction = milkTransaction;
        Log.d(TAG, "dataChangedUpdateDialogInfo() mMilkTransaction : " + mMilkTransaction);

        mDateTextView.setText(String.valueOf(TimeUtils.convertTimestampToDateString(mMilkTransaction.getTransactionDate())));
        mMilkQuantityEditText.setText((String.valueOf(mMilkTransaction.getMilkQuantityLiters())));
        mMilkTypeSpinner.setSelection(mMilkTransaction.getMilkType() - 1);
        mMilkPriceEditText.setText(String.valueOf(mMilkTransaction.getPricePerLiter()));
    }

    private AdapterView.OnItemSelectedListener getSpinnerItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (mMilkTransaction.getMilkType() == pos + 1) {
                    return;
                }
                mMilkTransaction.setMilkType(pos + 1);
                mMilkTransaction.setTransactionDate(TimeUtils.convertStringToTimestamp(mDateTextView.getText().toString()));
                mMilkTransaction.setPricePerLiter(Float.parseFloat(mMilkPriceEditText.getText().toString()));
                mMilkTransaction.setMilkQuantityLiters(Float.parseFloat(mMilkQuantityEditText.getText().toString()));

                mViewActionListener.updateMilkType(mMilkTransaction);
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

    public void setDialogButtonClickListener(AlertDialog alertDialog) {

        Optional.ofNullable(alertDialog)
                .map(dialog -> dialog.getButton(AlertDialog.BUTTON_POSITIVE))
                .ifPresent(button -> button.setOnClickListener(v -> {
                    if (createMilkTransactionAndInsert()) {
                        alertDialog.dismiss();
                    }
                }));
    }
}