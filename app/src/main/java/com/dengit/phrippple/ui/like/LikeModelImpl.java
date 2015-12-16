package com.dengit.phrippple.ui.like;

import android.text.TextUtils;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.LikeShot;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.BaseModelImpl;
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
public class LikeModelImpl<T> extends BaseModelImpl<T> implements LikeModel<T> {

    private LikePresenter<T> mPresenter;
    private int mUserId;

    public LikeModelImpl(LikePresenter<T> presenter) {
        super(presenter);
        mPresenter = presenter;
    }

    @Override
    public void setUserId(int userId) {
        mUserId = userId;
    }

    @Override
    protected void fetchItems(final int page) {

        final ArrayList<T> newShots = new ArrayList<>();

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
                    @SuppressWarnings("unchecked")
                    public void onNext(List<LikeShot> likeShots) {
                        Timber.d("**likeShots.size(): %d", likeShots.size());
                        for (LikeShot likeShot : likeShots) {
                            newShots.add((T)likeShot.shot);
                        }
                    }
                });
    }
}
