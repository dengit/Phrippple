package com.dengit.phrippple.ui;

import android.text.TextUtils;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.LikeShot;
import com.dengit.phrippple.data.Shot;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/16.
 */
public abstract class BaseModelImpl<T> implements BaseModel<T> {

    protected int mCurrPage = 0;
    protected DribbbleAPI mDribbbleAPI;
    protected String mAccessToken;
    private BasePresenter<T> mBasePresenter;

    public BaseModelImpl(BasePresenter<T> basePresenter) {
        mBasePresenter = basePresenter;
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
        mAccessToken = DribbbleAPIHelper.getInstance().getAccessToken();
    }

    @Override
    public void loadMore() {
        fetchItems(mCurrPage + 1);
    }

    @Override
    public boolean checkIfCanRefresh() {
        return !TextUtils.isEmpty(mAccessToken);
    }

    @Override
    public void loadNewest() {
        fetchItems(1);
    }

    protected abstract void fetchItems(final int page);

}

