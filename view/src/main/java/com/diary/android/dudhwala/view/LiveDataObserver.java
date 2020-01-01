package com.diary.android.dudhwala.view;

import com.diary.android.dudhwala.viewmodel.LiveDataSource.*;
import com.diary.android.dudhwala.viewmodel.ViewActionListener.*;

public interface LiveDataObserver {

   interface AddEditLiveDataObserver{
        void startObservingLiveData(AddEditLiveDataSource liveDataSource, AddEditViewActionListner viewActionListener);
    }

}
