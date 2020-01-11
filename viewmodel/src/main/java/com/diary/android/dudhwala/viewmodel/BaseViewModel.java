package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.model.IRepositoryFactory;

public interface BaseViewModel {

    boolean isNewInstance();

    void markAsOldInstance();

    void injectRepositoryFactory(IRepositoryFactory repositoryFactory);

    void injectLiveDataManager();
}
