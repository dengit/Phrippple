package com.dengit.phrippple.ui.like;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.BasePresenterImpl;

import java.util.List;

/**
 * Created by dengit on 15/12/14.
 */
public class LikePresenterImpl<T> extends BasePresenterImpl<T> implements LikePresenter<T> {
    private LikeView<T> mLikeView;
    private LikeModel<T> mLikeModel;

    public LikePresenterImpl(LikeView<T> likeView) {
        super(likeView);
        mLikeView = likeView;
        mLikeModel = new LikeModelImpl<T>(this);
        setBaseModel(mLikeModel);
    }

    @Override
    public void firstFetchItems() {
        mLikeModel.setUserId(mLikeView.getUserId());
        super.firstFetchItems();
    }


}
