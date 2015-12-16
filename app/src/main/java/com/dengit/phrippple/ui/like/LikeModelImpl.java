package com.dengit.phrippple.ui.like;

import android.text.TextUtils;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.LikeShot;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.utils.EventBusUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class LikeModelImpl implements LikeModel {

    private LikePresenter mPresenter;
    private DribbbleAPI mDribbbleAPI;
    private String mAccessToken;
    private int mCurrPage = 0;
    private int mUserId;

    public LikeModelImpl(LikePresenter presenter) {
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
        mAccessToken = DribbbleAPIHelper.getInstance().getAccessToken();
        mPresenter = presenter;
    }

    @Override
    public void loadMore() {
        fetchLikeShots(mCurrPage + 1);
    }

    @Override
    public boolean checkIfCanRefresh() {
        return !TextUtils.isEmpty(mAccessToken);
    }

    @Override
    public void loadNewest() {
        fetchLikeShots(1);
    }

    @Override
    public void setUserId(int userId) {
        mUserId = userId;
    }

    private void fetchLikeShots(final int page) {

        final ArrayList<Shot> newShots = new ArrayList<>();

        mDribbbleAPI.getLikeShots(mUserId, page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LikeShot>>() {
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
                    public void onNext(List<LikeShot> likeShots) {
                        Timber.d("**likeShots.size(): %d", likeShots.size());
                        for (LikeShot likeShot : likeShots) {
                            newShots.add(likeShot.shot);
                        }
                    }
                });
    }
}
