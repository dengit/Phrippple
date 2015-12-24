package com.dengit.phrippple;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/7.
 */
public class APP extends Application {

    private static APP mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        mInstance = this;
        Timber.plant(new Timber.DebugTree());
        Fresco.initialize(getApplicationContext());
        setupComponent();
        LeakCanary.install(this);
        // TODO: Move this to where you establish a user session
        logUser();
        MobclickAgent.setCatchUncaughtExceptions(false);
    }

    public static APP getInstance() {
        return mInstance;
    }

    private void setupComponent() {
        DaggerAPPComponent.builder()
                .aPPModule(new APPModule(this))
                .build();
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }
}
