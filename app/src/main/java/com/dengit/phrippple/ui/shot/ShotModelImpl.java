package com.dengit.phrippple.ui.shot;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.LikeShotResponse;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotModelImpl implements ShotModel {

    private ShotPresenter mPresenter;
    private int mShotId;
    protected DribbbleAPI mDribbbleAPI;
    protected String mAccessToken;

    public ShotModelImpl(ShotPresenter presenter) {
        mPresenter = presenter;
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
        mAccessToken = DribbbleAPIHelper.getInstance().getAccessToken();
    }

    @Override
    public void setShotId(int shotId) {
        mShotId = shotId;
    }

    @Override
    public void checkLikeStatus() {
        mDribbbleAPI.checkLikeShot(mShotId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LikeShotResponse>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");
                        mPresenter.updateLikeStatus();
                    }

                    @Override
                    public void onError(Throwable e) {// if the user does not like the shot
                        Timber.d("**onError:%s", e.toString());
                    }

                    @Override
                    @SuppressWarnings("unchecked")
                    public void onNext(LikeShotResponse response) {
                        Timber.d("**response.id: %d", response.id);
                    }
                });
    }

    @Override
    public void setLikeStatus(boolean status) {
        if (status) {
            doLikeShot();
        } else {
            undoLikeShot();
        }
    }

    private void doLikeShot() {
        mDribbbleAPI.likeShot(mShotId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LikeShotResponse>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {// if the user does not like the shot

                        Timber.d("**onError:%s", e.toString());
                    }

                    @Override
                    public void onNext(LikeShotResponse response) {
                        Timber.d("**response.id: %d", response.id);
                    }
                });
    }

    private void undoLikeShot() {
        mDribbbleAPI.unlikeShot(mShotId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");
                        //                        if (page == 1) {
                        //                            mPresenter.onLoadNewestFinished(newItems);
                        //                        } else {
                        //                            mPresenter.onLoadMoreFinished(newItems);
                        //                        }

                    }

                    @Override
                    public void onError(Throwable e) {// if the user does not like the shot

                        Timber.d("**onError:%s", e.toString());
//                        e.printStackTrace();
                        //                        mPresenter.onError();
                    }

                    @Override
                    public void onNext(Void response) {
                        Timber.d("**Void response onNext");
                    }
                });

    }
}
