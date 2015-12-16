package com.dengit.phrippple.ui.main;

import android.text.TextUtils;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.RequestTokenBody;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.TokenInfo;
import com.dengit.phrippple.utils.EventBusUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/9.
 */
public class MainModelImpl implements MainModel {
    private MainPresenter mMainPresenter;
    private int mCurrPage = 0;
    private DribbbleAPI mDribbbleAPI;
    private String mAccessToken;


    public MainModelImpl(MainPresenter mainPresenter) {
        mMainPresenter = mainPresenter;
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
    }

    @Override
    public void loadMore() {
        fetchShots(mCurrPage + 1);
    }

    @Override
    public boolean checkIfCanRefresh() {
        return !TextUtils.isEmpty(mAccessToken);
    }

    @Override
    public void loadNewest() {
        fetchShots(1);
    }

    private void fetchShots(final int page) {

        final ArrayList<Shot> newShots = new ArrayList<>();

        mDribbbleAPI.getShots(page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");
                        mCurrPage = page;
                        if (page == 1) {
                            mMainPresenter.onLoadNewestFinished(newShots);
                        } else {
                            mMainPresenter.onLoadMoreFinished(newShots);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.d("**onError");
                        e.printStackTrace();
                        mMainPresenter.onError();
                    }

                    @Override
                    public void onNext(List<Shot> shots) {
                        Timber.d("**shots.size(): %d", shots.size());
                        newShots.addAll(shots);
                    }
                });
    }

    @Override
    public void requestToken(AuthorizeInfo info) {

        mDribbbleAPI.getToken(new RequestTokenBody(info.getCode()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TokenInfo tokenInfo) {
                        Timber.d("**token:%s", tokenInfo.access_token);
                        EventBusUtil.getInstance().post(tokenInfo);
                    }
                });
    }

    @Override
    public void setToken(TokenInfo tokenInfo) {
        mAccessToken = tokenInfo.access_token;
        DribbbleAPIHelper.getInstance().setAccessTokenInfo(tokenInfo);
    }
}
