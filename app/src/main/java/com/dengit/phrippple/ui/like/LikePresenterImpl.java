package com.dengit.phrippple.ui.like;

/**
 * Created by dengit on 15/12/14.
 */
public class LikePresenterImpl implements LikePresenter {
    private LikeView mLikeView;
    private LikeModel mLikeModel;

    public LikePresenterImpl(LikeView likeView) {
        mLikeView = likeView;
        mLikeModel = new LikeModelImpl();
    }

    @Override
    public void onResume(int userId) {
        mLikeModel.fetchLikeShots(userId);
    }
}
