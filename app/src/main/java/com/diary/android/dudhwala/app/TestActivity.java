package com.diary.android.dudhwala.app;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import com.diary.android.dudhwala.R;

import java.util.Calendar;

public class TestActivity extends AppCompatActivity {

    private static final int MIN_YEARS = 30;
    private static final int NO_OF_MONTHS = 12;
    private final String[] mMonths = new String[]{
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"
    };
    private TextSwitcher mSwitcher;
    private int count;
    private int mCurrentMonth;
    private int mCurrentYear;
    private int mSelectedMonth;
    private int mSelectedYear;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_calendar_view);

        getCurrentMonthAndYear();
        mSwitcher = findViewById(R.id.textSwitcher);
        mSwitcher.setCurrentText(mMonths[mSelectedMonth] + " " + mSelectedYear);

        mNextButton = findViewById(R.id.navigateNextButton);
        mPreviousButton = findViewById(R.id.navigatePreviousButton);
        mNextButton.setOnClickListener(v -> showNextMonth());
        mPreviousButton.setOnClickListener(v -> showPreviousMonth());

        if (isSelectedMonthCurrent()) {
            mNextButton.setEnabled(false);
        } else {
            mNextButton.setEnabled(true);
        }

    }

    private boolean isSelectedMonthCurrent() {
        return mSelectedYear == mCurrentYear && mSelectedMonth == mCurrentMonth;
    }

    private void getCurrentMonthAndYear() {

        Calendar calendar = Calendar.getInstance();
        mSelectedMonth = mCurrentMonth = calendar.get(Calendar.MONTH);
        mSelectedYear = mCurrentYear = calendar.get(Calendar.YEAR);
    }

    private void showPreviousMonth() {
        mSwitcher.setInAnimation(getBaseContext(), android.R.anim.slide_in_left);
        mSwitcher.setOutAnimation(getBaseContext(), android.R.anim.slide_out_right);

        mSelectedMonth--;
        if (mSelectedMonth == -1) {
            mSelectedMonth = NO_OF_MONTHS - 1;
            mSelectedYear--;
        }
        mSwitcher.setText(mMonths[mSelectedMonth] + " " + mSelectedYear);

        updateNextButtonEnabledState();
    }

    private void updateNextButtonEnabledState() {
        if (isSelectedMonthCurrent()) {
            mNextButton.setEnabled(false);
        } else {
            mNextButton.setEnabled(true);
        }
    }

    public void showNextMonth() {
        mSwitcher.setInAnimation(getBaseContext(), R.anim.slide_in_right);
        mSwitcher.setOutAnimation(getBaseContext(), R.anim.slide_out_left);

        mSelectedMonth = (mSelectedMonth + 1) % NO_OF_MONTHS;
        if (mSelectedMonth == 0) {
            mSelectedYear++;
        }
        mSwitcher.setText(mMonths[mSelectedMonth] + " " + mSelectedYear);

        updateNextButtonEnabledState();
    }
}
