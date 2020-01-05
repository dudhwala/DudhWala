package com.diary.android.dudhwala.view;

import android.os.Bundle;

import androidx.annotation.NonNull;

public interface IActivityActionListener {

    interface IMilkTransactionsActivity {

    }

    interface IDialogFragmentActionListener {
        void onSaveInstanceState(@NonNull Bundle outState);
    }
}
