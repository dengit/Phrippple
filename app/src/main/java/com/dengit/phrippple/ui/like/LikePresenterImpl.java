package com.dengit.phrippple.ui.like;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.Shot;

import java.util.List;

/**
 * Created by dengit on 15/12/14.
 */
public class LikePresenterImpl implements LikePresenter {
    private LikeView mLikeView;
    private LikeModel mLikeModel;

    public LikePresenterImpl(LikeView likeView) {
        mLikeView = likeView;
        mLikeModel = new LikeModelImpl(this);
    }

    @Override
    public void onFirstFetchShots() {
        mLikeModel.setUserId(mLikeView.getUserId());
        fetchNewestShots(false);
    }

    @Override
    public void fetchNewestShots(boolean isRefresh) {
        if (isRefresh) {
            if (mLikeModel.checkIfCanRefresh()) {
                mLikeView.switchRefresh(true);
                mLikeModel.loadNewest();
            }
        } else {
            mLikeModel.loadNewest();
        }
    }

    @Override
    public void onLoadNewestFinished(List<Shot> newShots) {
        mLikeView.switchRefresh(false);
        mLikeView.setItems(newShots);
    }

    @Override
    public void onLoadMoreFinished(List<Shot> newShots) {
        mLikeView.switchLoadMore(false, newShots.size() < DribbbleAPI.LIMIT_PER_PAGE);
        mLikeView.appendItems(newShots);
    }

    @Override
    public void onError() {
        mLikeView.switchRefresh(false);
    }

    @Override
    public void onFooterClick() {
        mLikeView.switchLoadMore(true, false);
        mLikeModel.loadMore();
    }
}
