package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.BucketType;

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
    public void onResume(BucketType bucketType, int id) {
        mBucketModel.fetchBuckets(bucketType, id);
    }
}
