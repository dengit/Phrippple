package com.dengit.phrippple.ui;

import android.text.TextUtils;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;

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

