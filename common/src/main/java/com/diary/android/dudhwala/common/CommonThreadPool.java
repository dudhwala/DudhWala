package com.diary.android.dudhwala.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommonThreadPool {

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private static final ExecutorService threadPool =
            Executors.newFixedThreadPool(NUMBER_OF_CORES);

    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
