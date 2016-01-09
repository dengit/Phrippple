package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.ui.base.FetchBaseModelImpl;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketModelImpl extends FetchBaseModelImpl<Bucket> implements BucketModel {

    private int mId;
    private BucketType mBucketType;
    private BucketPresenter mPresenter;

    public BucketModelImpl(BucketPresenter presenter) {
        super(presenter);
        mPresenter = presenter;
    }

    @Override
    public void setId(int id) {
        mId = id;
    }

    @Override
    public void setBucketType(BucketType bucketType) {
        mBucketType = bucketType;
    }

    @Override
    protected void fetchItems(final int page) {
        final ArrayList<Bucket> newItems = new ArrayList<>();

        Observable<List<Bucket>> observable;
        if (mBucketType == BucketType.BucketsOfSelf) {
            observable = mDribbbleAPI.getMineBuckets(mId, page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken);
        } else {
            observable = mDribbbleAPI.getOthersBuckets(mId, page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken);
        }

        mSubscriptions.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bucket>>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");
                        mCurrPage = page;
                        if (page == 1) {
                            mPresenter.onLoadNewestFinished(newItems);
                        } else {
                            mPresenter.onLoadMoreFinished(newItems);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.d("**onError");
                        e.printStackTrace();
                        mPresenter.onError();
                    }


                    @Override
                    public void onNext(List<Bucket> buckets) {
                        Timber.d("**buckets.size(): %d", buckets.size());
                        newItems.addAll(buckets);
                    }
                })
        );
    }
}
