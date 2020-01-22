package com.diary.android.dudhwala.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.R;
import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.view.ILiveDataObserver.ICustomCalendarLiveDataObserver;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.ICustomCalendarLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.ICustomCalendarViewActionListener;
import com.diary.android.dudhwala.viewmodel.data.DurationData;

public class CustomCalendarView extends FrameLayout implements ICustomCalendarLiveDataObserver {

    private static final int MIN_YEARS = 20;
    private static final int NO_OF_MONTHS = 12;
    private final String[] mMonths = new String[]{
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"
    };
    private Context mContext;
    private LifecycleOwner mLifeCycleOwner;
    private TextSwitcher mSwitcher;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;

    private ICustomCalendarLiveDataSource mLiveDataSource;
    private ICustomCalendarViewActionListener mCustomCalendarActionListener;

    public CustomCalendarView(Context context) {
        super(context);
        this.mContext = context;
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

    private void init() {
        inflate(mContext, R.layout.custom_calendar_view, this);

        mSwitcher = findViewById(R.id.textSwitcher);
        mNextButton = findViewById(R.id.navigateNextButton);
        mNextButton.setOnClickListener(v -> showNextMonth());
        mPreviousButton = findViewById(R.id.navigatePreviousButton);
        mPreviousButton.setOnClickListener(v -> showPreviousMonth());
    }

    public void injectLifeCycleOwner(Context context, LifecycleOwner lifecycleOwner) {
        mLifeCycleOwner = lifecycleOwner;
    }

    @Override
    public void startObservingLiveData(
            ICustomCalendarLiveDataSource liveDataSource,
            ICustomCalendarViewActionListener customCalendarViewActionListener) {

        mLiveDataSource = liveDataSource;
        mCustomCalendarActionListener = customCalendarViewActionListener;

        mCustomCalendarActionListener.initializeCalendar();
        mLiveDataSource.provideDurationLiveData().ifPresent(this::setDurationObserver);
    }

    private void setDurationObserver(LiveData<DurationData> durationDataLiveData) {

        durationDataLiveData.observe(mLifeCycleOwner, durationData -> {
            updateTextAndButtonEnabledState(durationData);
            mCustomCalendarActionListener.onDurationChange(durationData.getSelectedMonth(), durationData.getSelectedYear());
        });
    }

    private boolean isSelectedMonthCurrent(DurationData durationData) {
        return durationData.getCurrentYear() == durationData.getSelectedYear()
                && durationData.getCurrentMonth() == durationData.getSelectedMonth();
    }

    private void showPreviousMonth() {
        mSwitcher.setInAnimation(mContext, android.R.anim.slide_in_left);
        mSwitcher.setOutAnimation(mContext, android.R.anim.slide_out_right);
        mCustomCalendarActionListener.onClickCustomCalenderButton(Constants.ClickedButton.PREVIOUS);
    }


    public void showNextMonth() {
        mSwitcher.setInAnimation(mContext, R.anim.slide_in_right);
        mSwitcher.setOutAnimation(mContext, R.anim.slide_out_left);
        mCustomCalendarActionListener.onClickCustomCalenderButton(Constants.ClickedButton.NEXT);
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
}
