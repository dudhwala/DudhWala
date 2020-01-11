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

import com.diary.android.dudhwala.common.Constants;
import com.diary.android.dudhwala.viewmodel.ILiveDataSource.ICustomCalendarLiveDataSource;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.ICustomCalendarActionListener;
import com.diary.android.dudhwala.viewmodel.IViewActionListener.MilkTransactionViewActionListener;
import com.diary.android.dudhwala.viewmodel.data.DurationData;

public class CustomCalendarView extends FrameLayout {

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
    private ICustomCalendarActionListener mCustomCalendarActionListener;
    private MilkTransactionViewActionListener mMilkTransactionViewActionListener;

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
        inflate(mContext, com.diary.android.dudhwala.view.R.layout.custom_calendar_view, this);

        mSwitcher = findViewById(com.diary.android.dudhwala.view.R.id.textSwitcher);
        mNextButton = findViewById(com.diary.android.dudhwala.view.R.id.navigateNextButton);
        mNextButton.setOnClickListener(v -> showNextMonth());
        mPreviousButton = findViewById(com.diary.android.dudhwala.view.R.id.navigatePreviousButton);
        mPreviousButton.setOnClickListener(v -> showPreviousMonth());
    }

    public void injectLifeCycleOwner(Context context, LifecycleOwner lifecycleOwner) {
        mLifeCycleOwner = lifecycleOwner;
    }

    public void startObservingLiveData(
            ICustomCalendarLiveDataSource liveDataSource,
            ICustomCalendarActionListener customCalendarViewActionListener,
            MilkTransactionViewActionListener milkTransactionViewActionListener) {

        mLiveDataSource = liveDataSource;
        mCustomCalendarActionListener = customCalendarViewActionListener;
        mMilkTransactionViewActionListener = milkTransactionViewActionListener;

        mCustomCalendarActionListener.initializeCalendar();
        mLiveDataSource.provideDurationLiveData().ifPresent(this::setDurationObserver);
    }

    private void setDurationObserver(LiveData<DurationData> durationDataLiveData) {

        durationDataLiveData.observe(mLifeCycleOwner, durationData -> {
            updateTextAndButtonEnabledState(durationData);
            mMilkTransactionViewActionListener.onDurationChange(durationData.getSelectedMonth(), durationData.getSelectedYear());
        });
    }

    private boolean isSelectedMonthCurrent(DurationData durationData) {
        return durationData.getCurrentYear() == durationData.getSelectedYear()
                && durationData.getCurrentMonth() == durationData.getSelectedMonth();
    }

    private void showPreviousMonth() {
        mSwitcher.setInAnimation(mContext, android.R.anim.slide_in_left);
        mSwitcher.setOutAnimation(mContext, android.R.anim.slide_out_right);
        mCustomCalendarActionListener.onClickButton(Constants.ClickedButton.PREVIOUS);
    }


    public void showNextMonth() {
        mSwitcher.setInAnimation(mContext, com.diary.android.dudhwala.view.R.anim.slide_in_right);
        mSwitcher.setOutAnimation(mContext, com.diary.android.dudhwala.view.R.anim.slide_out_left);
        mCustomCalendarActionListener.onClickButton(Constants.ClickedButton.NEXT);
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
