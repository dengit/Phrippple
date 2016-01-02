package com.dengit.phrippple;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.dengit.phrippple.injection.component.APPComponent;
import com.dengit.phrippple.injection.component.DaggerAPPComponent;
import com.dengit.phrippple.injection.module.APPModule;
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
    private APPComponent mAPPComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Fabric.with(this, new Crashlytics());
        Fresco.initialize(getApplicationContext());
        // TODO: Move this to where you establish a user session
        logUser();
        MobclickAgent.setCatchUncaughtExceptions(false);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            LeakCanary.install(this);
        }
    }

    public static APP getInstance() {
        return mInstance;
    }

    public APPComponent getComponent() {
        if (mAPPComponent == null) {
            mAPPComponent = DaggerAPPComponent.builder()
                    .aPPModule(new APPModule(this))
                    .build();
        }

        return mAPPComponent;
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }
}
