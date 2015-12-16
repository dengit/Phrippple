package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.TokenInfo;

import java.util.List;

/**
 * Created by dengit on 15/12/9.
 */
public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private MainModel mMainModel;

    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
        mMainModel = new MainModelImpl(this); //todo use DI?
    }

    @Override
    public void onFooterClick() {
        mMainView.switchLoadMore(true, false);
        mMainModel.loadMore();
    }

    @Override
    public void onLoadNewestFinished(List<Shot> newShots) {
        mMainView.switchRefresh(false);
        mMainView.setItems(newShots);
    }

    @Override
    public void onLoadMoreFinished(List<Shot> newShots) {
        mMainView.switchLoadMore(false, newShots.size() < DribbbleAPI.LIMIT_PER_PAGE);
        mMainView.appendItems(newShots);
    }

    @Override
    public void onError() {
        mMainView.switchRefresh(false);
    }

    @Override
    public void requestToken(AuthorizeInfo info) {
        mMainModel.requestToken(info);
    }

    @Override
    public void fetchNewestShots(boolean isRefresh) {
        if (isRefresh) {
            if (mMainModel.checkIfCanRefresh()) {
                mMainView.switchRefresh(true);
                mMainModel.loadNewest();
            }
        } else {
            mMainModel.loadNewest();
        }
    }

    @Override
    public void onFirstFetchShots(TokenInfo tokenInfo) {
        mMainModel.setToken(tokenInfo);
        fetchNewestShots(false);
    }
}
