package com.dengit.phrippple.utils;

import com.squareup.otto.Bus;

/**
 * Created by dengit on 15/12/7.
 */
public class EventBus {

    private static Bus mBus;

    private EventBus() {

    }

    public static Bus getInstance() {
        if (mBus == null) {
            synchronized (EventBus.class) {
                if (mBus == null) {
                    mBus = new Bus();
                }
            }
        }
        return mBus;
    }

}
