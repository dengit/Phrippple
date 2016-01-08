package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketPresenterImpl extends FetchBasePresenterImpl<Bucket> implements BucketPresenter {
    private BucketView mBucketView;
    private BucketModel mBucketModel;

    public BucketPresenterImpl(BucketView bucketView) {
        super(bucketView);
        mBucketView = bucketView;
        mBucketModel = new BucketModelImpl(this);
        setBaseModel(mBucketModel);
    }

    @Override
    public void firstFetchItems() {
        mBucketModel.setId(mBucketView.getId());
        mBucketModel.setBucketType(mBucketView.getBucketType());
        super.firstFetchItems();
    }
}
