package com.diary.android.dudhwala.app;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.viewmodel.ICustomCalendarViewModel;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener;
import com.diary.android.dudhwala.viewmodel.data.DurationData;
import com.diary.android.dudhwala.viewmodelimpl.viewmodel.CustomCalendarViewModelImpl;

public class CustomCalendarView extends FrameLayout {

    private static final int MIN_YEARS = 20;
    private static final int NO_OF_MONTHS = 12;
    private final String[] mMonths = new String[]{
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"
    };
    private Context mContext;
    private TextSwitcher mSwitcher;
    private CalendarActionListener mCalendarActionListener;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;

    private ICustomCalendarViewModel mCustomCalendarViewModel;

    private ILiveDataSource.ICustomCalendarLiveDataSource mLiveDataSource;
    private IViewActionListener.ICustomCalendarActionListener mViewActionListener;

    public CustomCalendarView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private ICustomCalendarViewModel getViewModel() {
        return ViewModelProviders.of((AppCompatActivity) mContext).get(CustomCalendarViewModelImpl.class);
    }

    private void init() {
        inflate(mContext, com.diary.android.dudhwala.view.R.layout.custom_calendar_view, this);

        mCustomCalendarViewModel = getViewModel();
        if (mCustomCalendarViewModel.isNewInstance()) {
            mCustomCalendarViewModel.markAsOldInstance();
        }
        mLiveDataSource = mCustomCalendarViewModel;
        mViewActionListener = mCustomCalendarViewModel;
        mCalendarActionListener = (CalendarActionListener) mContext;

        startObservingLiveData();

        mViewActionListener.setCurrentMonthAndYear();

        mSwitcher = findViewById(com.diary.android.dudhwala.view.R.id.textSwitcher);
        mNextButton = findViewById(com.diary.android.dudhwala.view.R.id.navigateNextButton);
        mPreviousButton = findViewById(com.diary.android.dudhwala.view.R.id.navigatePreviousButton);
        mNextButton.setOnClickListener(v -> showNextMonth());
        mPreviousButton.setOnClickListener(v -> showPreviousMonth());

    }

    private boolean isSelectedMonthCurrent(DurationData durationData) {
        return durationData.getCurrentYear() == durationData.getSelectedYear()
                && durationData.getCurrentMonth() == durationData.getSelectedMonth();
    }

    private void showPreviousMonth() {
        mSwitcher.setInAnimation(mContext, android.R.anim.slide_in_left);
        mSwitcher.setOutAnimation(mContext, android.R.anim.slide_out_right);
        mViewActionListener.clickButton(Constants.ClickedButton.PREVIOUS);
    }


    public void showNextMonth() {

        mSwitcher.setInAnimation(mContext, com.diary.android.dudhwala.view.R.anim.slide_in_right);
        mSwitcher.setOutAnimation(mContext, com.diary.android.dudhwala.view.R.anim.slide_out_left);

        mViewActionListener.clickButton(Constants.ClickedButton.NEXT);
    }

    public void startObservingLiveData() {
        mLiveDataSource.provideDurationLiveData().ifPresent(this::setDurationStringObserver);
    }

    private void setDurationStringObserver(LiveData<DurationData> durationDataLiveData) {

        durationDataLiveData.observe((LifecycleOwner) mContext, durationData -> {
            updateTextAndButtonEnabledState(durationData);
            mCalendarActionListener.onUpdateDuration(durationData.getSelectedMonth(), durationData.getSelectedYear());
        });
    }


    private void updateTextAndButtonEnabledState(DurationData durationData) {

        mSwitcher.setText(mMonths[durationData.getSelectedMonth()] + " " + durationData.getSelectedYear());

        if (isSelectedMonthCurrent(durationData)) {
            mNextButton.setAlpha(0.4f);
            mNextButton.setEnabled(false);
        } else {
            mNextButton.setAlpha(1f);
            mNextButton.setEnabled(true);
        }

        if (durationData.getSelectedMonth() == 0
                && durationData.getSelectedYear() == durationData.getCurrentYear() - MIN_YEARS) {
            mPreviousButton.setAlpha(0.4f);
            mPreviousButton.setEnabled(false);
        } else {
            mPreviousButton.setAlpha(1f);
            mPreviousButton.setEnabled(true);
        }
    }

    interface CalendarActionListener {

        void onUpdateDuration(int month, int year);
    }
}
