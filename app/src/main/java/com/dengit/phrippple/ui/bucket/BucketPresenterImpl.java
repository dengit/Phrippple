package com.dengit.phrippple.ui.bucket;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketPresenterImpl implements BucketPresenter {
    private BucketView mBucketView;
    private BucketModel mBucketModel;

    public BucketPresenterImpl(BucketView likeView) {
        mBucketView = likeView;
        mBucketModel = new BucketModelImpl();
    }

    @Override
    public void onResume(int userId) {
        mBucketModel.fetchBuckets(userId);
    }
}
