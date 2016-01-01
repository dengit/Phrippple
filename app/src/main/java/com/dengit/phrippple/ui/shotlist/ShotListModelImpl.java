package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.LikeShot;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.BaseModelImpl;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotListModelImpl<T> extends BaseModelImpl<T> implements ShotListModel<T> {

    private int mId;
    private ShotListType mShotListType;
    private ShotListPresenter<T> mPresenter;

    public ShotListModelImpl(ShotListPresenter<T> presenter) {
        super(presenter);
        mPresenter = presenter;
    }

    @Override
    public void setId(int id) {
        mId = id;
    }

    @Override
    public void setShotListType(ShotListType shotListType) {
        mShotListType = shotListType;
    }

    @Override
    protected void fetchItems(final int page) {

        final ArrayList<T> newShots = new ArrayList<>();

        Subscriber<Shot> subscriber = new Subscriber<Shot>() {
            @Override
            public void onCompleted() { //todo use eventbus
                Timber.d("**onCompleted");
                mCurrPage = page;
                if (page == 1) {
                    mPresenter.onLoadNewestFinished(newShots);
                } else {
                    mPresenter.onLoadMoreFinished(newShots);
                }

            }

            @Override
            public void onError(Throwable e) {

                Timber.d("**onError");
                e.printStackTrace();
                mPresenter.onError();
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onNext(Shot shot) {
                newShots.add((T) shot);
            }
        };

        if (mShotListType == ShotListType.ShotsOfLikes) {
            mDribbbleAPI.getLikeShots(mId, page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                    .flatMap(new Func1<List<LikeShot>, Observable<LikeShot>>() {
                        @Override
                        public Observable<LikeShot> call(List<LikeShot> likeShots) {
                            return Observable.from(likeShots);
                        }
                    })
                    .map(new Func1<LikeShot, Shot>() {
                        @Override
                        public Shot call(LikeShot likeShot) {
                            return likeShot.shot;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else if (mShotListType == ShotListType.ShotsOfBucket) {
            mDribbbleAPI.getBucketShots(mId, page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                    .flatMap(new Func1<List<Shot>, Observable<Shot>>() {
                        @Override
                        public Observable<Shot> call(List<Shot> shots) {
                            return Observable.from(shots);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else if (mShotListType == ShotListType.ShotsOfSelf) {
            mDribbbleAPI.getSelfShots(mId, page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                    .flatMap(new Func1<List<Shot>, Observable<Shot>>() {
                        @Override
                        public Observable<Shot> call(List<Shot> shots) {
                            return Observable.from(shots);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else if (mShotListType == ShotListType.ShotsOfFollowing) {
            mDribbbleAPI.getUserFollowingShots(page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                    .flatMap(new Func1<List<Shot>, Observable<Shot>>() {
                        @Override
                        public Observable<Shot> call(List<Shot> shots) {
                            return Observable.from(shots);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

    }
}
