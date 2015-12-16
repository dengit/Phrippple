package com.dengit.phrippple.ui.main;

import android.text.TextUtils;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.RequestTokenBody;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.TokenInfo;
import com.dengit.phrippple.ui.BaseModel;
import com.dengit.phrippple.ui.BaseModelImpl;
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
public class MainModelImpl<T> extends BaseModelImpl<T> implements MainModel<T> {
    private MainPresenter<T> mMainPresenter;

    public MainModelImpl(MainPresenter<T> mainPresenter) {
        super(mainPresenter);
        mMainPresenter = mainPresenter;
    }

    @Override
    protected void fetchItems(final int page) {
        final ArrayList<T> newItems = new ArrayList<>();
        mDribbbleAPI.getShots(page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");
                        mCurrPage = page;
                        if (page == 1) {
                            mMainPresenter.onLoadNewestFinished(newItems);
                        } else {
                            mMainPresenter.onLoadMoreFinished(newItems);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.d("**onError");
                        e.printStackTrace();
                        mMainPresenter.onError();
                    }

                    @Override
                    @SuppressWarnings("unchecked")
                    public void onNext(List<Shot> shots) {
                        Timber.d("**Shots.size(): %d", shots.size());
                        newItems.addAll((List<T>)shots);
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
}
