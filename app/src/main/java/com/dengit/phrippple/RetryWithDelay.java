package com.dengit.phrippple;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by dengit on 16/1/3.
 */
public class RetryWithDelay
        implements Func1<Observable<? extends Throwable>, Observable<?>> {
    private final int mMaxRetries;
    private final int mRetryDelayMillis;
    private int mRetryCount;

    public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
        mMaxRetries = maxRetries;
        mRetryDelayMillis = retryDelayMillis;
        mRetryCount = 0;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                if (++mRetryCount < mMaxRetries) {
                    Timber.d("**Retry in %d ms", mRetryCount * mRetryDelayMillis);
                    return Observable.timer(mRetryCount * mRetryDelayMillis, TimeUnit.MILLISECONDS);
                }

                Timber.d("hit max retry count!");
                return Observable.error(throwable);
            }
        });
    }
}
