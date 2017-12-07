package com.good.worshipbible.nos.favorite;

import android.app.Application;

public class ModApplication extends Application {
    private static ModApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ModApplication getInstance() {
        return instance;
    }
}