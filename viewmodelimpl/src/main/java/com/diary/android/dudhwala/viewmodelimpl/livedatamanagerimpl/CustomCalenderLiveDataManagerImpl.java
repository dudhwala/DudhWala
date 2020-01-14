package com.diary.android.dudhwala.viewmodelimpl.livedatamanagerimpl;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.viewmodel.data.DurationData;
import com.diary.android.dudhwala.viewmodel.livedatamanager.ICustomCalenderLiveDataManager;

import java.util.Calendar;

import static com.diary.android.dudhwala.common.Constants.Log._TAG;

public class CustomCalenderLiveDataManagerImpl implements ICustomCalenderLiveDataManager {


    private static final int NO_OF_MONTHS = 12;
    private static final String TAG = _TAG + "/CustomCalendarViewModelImpl";
    private int mCurrentMonth;
    private int mCurrentYear;
    private int mSelectedMonth = 0;
    private int mSelectedYear = 0;

    private MutableLiveData<DurationData> mDurationLiveData = new MutableLiveData<>();

    @Override
    public void onClickCustomCalenderButton(int button) {
        Log.d(TAG, "onClickCustomCalenderButton() button : " + button);
        if (Constants.ClickedButton.NEXT == button) {
            mSelectedMonth = (mSelectedMonth + 1) % NO_OF_MONTHS;
            if (mSelectedMonth == 0) {
                mSelectedYear++;
            }
        } else if (Constants.ClickedButton.PREVIOUS == button) {
            mSelectedMonth--;
            if (mSelectedMonth == -1) {
                mSelectedMonth = NO_OF_MONTHS - 1;
                mSelectedYear--;
            }
        }

        updateDurationLiveData();
    }

    @Override
    public void initializeCalendar() {
        getCurrentMonthAndYear();
        updateDurationLiveData();
    }

    private void updateDurationLiveData() {

        if (mSelectedMonth == 0) {
            mSelectedMonth = mCurrentMonth;
        }
        if (mSelectedYear == 0) {
            mSelectedYear = mCurrentYear;
        }

        DurationData durationData = new DurationData();
        durationData.setCurrentMonth(mCurrentMonth);
        durationData.setCurrentYear(mCurrentYear);
        durationData.setSelectedMonth(mSelectedMonth);
        durationData.setSelectedYear(mSelectedYear);
        Log.d(TAG, "updateDurationLiveData() mSelectedMonth : " + mSelectedMonth
                + ", mSelectedYear : " + mSelectedYear);
        mDurationLiveData.setValue(durationData);
    }

    private void getCurrentMonthAndYear() {
        Calendar calendar = Calendar.getInstance();
        mCurrentMonth = calendar.get(Calendar.MONTH);
        mCurrentYear = calendar.get(Calendar.YEAR);
    }

    @Override
    public LiveData<DurationData> provideDurationLiveData() {
        return mDurationLiveData;
    }
}

