package com.diary.android.dudhwala.viewmodelimpl.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.model.IRepositoryFactory;
import com.diary.android.dudhwala.viewmodel.ICustomCalendarViewModel;
import com.diary.android.dudhwala.viewmodel.data.DurationData;

import java.util.Calendar;
import java.util.Optional;

public class CustomCalendarViewModelImpl extends ViewModel implements ICustomCalendarViewModel {

    private static final int NO_OF_MONTHS = 12;
    private static final String TAG = "DudhWala/CustomCalendarViewModelImpl";
    private boolean isNewInstance = true;
    private int mCurrentMonth;
    private int mCurrentYear;
    private int mSelectedMonth = 0;
    private int mSelectedYear = 0;

    private MutableLiveData<DurationData> mDurationLiveData = new MutableLiveData<>();

    @Override
    public boolean isNewInstance() {
        return isNewInstance;
    }

    @Override
    public void markAsOldInstance() {
        isNewInstance = false;
    }

    @Override
    public void injectRepositoryFactory(IRepositoryFactory repositoryFactory) {

    }

    @Override
    public void injectLiveDataManager() {

    }

    @Override
    public void onClickButton(int button) {
        Log.d(TAG, "onClickButton() button : " + button);
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

    @Override
    public void initializeCalendar() {
        getCurrentMonthAndYear();
        updateDurationLiveData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @Override
    public Optional<LiveData<DurationData>> provideDurationLiveData() {
        return Optional.ofNullable(mDurationLiveData);
    }

    private void getCurrentMonthAndYear() {

        Calendar calendar = Calendar.getInstance();
        mCurrentMonth = calendar.get(Calendar.MONTH);
        mCurrentYear = calendar.get(Calendar.YEAR);
    }
}
