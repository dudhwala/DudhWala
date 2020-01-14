package com.diary.android.dudhwala.viewmodel.livedatamanager;

import androidx.lifecycle.LiveData;

import com.diary.android.dudhwala.viewmodel.data.DurationData;

import java.util.Optional;

public interface ICustomCalenderLiveDataManager {

    LiveData<DurationData> provideDurationLiveData();

    void initializeCalendar();

    void onClickCustomCalenderButton(int button);

}
