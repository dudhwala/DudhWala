package com.diary.android.dudhwala.viewmodel;

import com.diary.android.dudhwala.model.RepositoryFactory;

public interface BaseViewModel {

    boolean isNewInstance();

    void markAsOldInstance();

    void injectRepositoryFactory(RepositoryFactory repositoryFactory);
}
