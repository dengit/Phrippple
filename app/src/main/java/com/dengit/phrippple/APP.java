package com.dengit.phrippple;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by dengit on 15/12/7.
 */
public class APP extends Application {

    private static APP mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Timber.plant(new Timber.DebugTree());
        Fresco.initialize(getApplicationContext());
        setupComponent();
        LeakCanary.install(this);
    }

    private void setupComponent() {
        DaggerAPPComponent.builder()
                .aPPModule(new APPModule(this))
                .build();
    }

    public static APP getInstance() {
        return mInstance;
    }
}
