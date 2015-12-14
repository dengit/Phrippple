package com.dengit.phrippple.utils;

import com.squareup.otto.Bus;

/**
 * Created by dengit on 15/12/7.
 */
public class EventBusUtil {

    private static Bus mBus;

    private EventBusUtil() {

    }

    public static Bus getInstance() {
        if (mBus == null) {
            synchronized (EventBusUtil.class) {
                if (mBus == null) {
                    mBus = new Bus();
                }
            }
        }
        return mBus;
    }

}
