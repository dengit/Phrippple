package com.dengit.phrippple;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by dengit on 15/12/7.
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

    }
}
