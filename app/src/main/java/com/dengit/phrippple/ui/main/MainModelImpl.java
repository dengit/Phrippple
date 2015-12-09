package com.dengit.phrippple.ui.main;

import android.text.TextUtils;
import android.view.View;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.model.AuthorizeInfo;
import com.dengit.phrippple.model.RequestTokenBody;
import com.dengit.phrippple.model.Shot;
import com.dengit.phrippple.model.TokenInfo;
import com.dengit.phrippple.utils.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
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
        mDribbbleAPI = new Retrofit.Builder()
                .baseUrl(DribbbleAPI.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(DribbbleAPI.class);
    }

    @Override
    public void loadMore() {
        ++mCurrPage;
        fetchShots(mAccessToken, mCurrPage);
    }

    @Override
    public boolean checkIfCanRefresh() {
        return !TextUtils.isEmpty(mAccessToken);
    }

    @Override
    public void loadNewest() {
        mCurrPage = 1;
        fetchShots(mAccessToken, mCurrPage);
    }

    private void fetchShots(String accessToken, final int page) {

        final List<Shot> newShots = new ArrayList<>();

        mDribbbleAPI.getShots(accessToken, page)
                .flatMap(new Func1<List<Shot>, Observable<Shot>>() {
                    @Override
                    public Observable<Shot> call(List<Shot> shots) {
                        return Observable.from(shots);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Shot>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("**onCompleted");

                        if (page == 1) {//todo maybe not refreshing
                            mMainPresenter.onRefreshFinished(newShots);
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
                    public void onNext(Shot shot) {
                        Timber.d(shot.title);
                        newShots.add(shot);
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
                        EventBus.getInstance().post(tokenInfo);
                    }
                });
    }

    @Override
    public void setToken(TokenInfo tokenInfo) {
        mAccessToken = tokenInfo.access_token;
    }
}
