package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketPresenterImpl<T> extends FetchBasePresenterImpl<T> implements BucketPresenter<T> {
    private BucketView<T> mBucketView;
    private BucketModel<T> mBucketModel;

    public BucketPresenterImpl(BucketView<T> bucketView) {
        super(bucketView);
        mBucketView = bucketView;
        mBucketModel = new BucketModelImpl<>(this);
        setBaseModel(mBucketModel);
    }

    @Override
    public void firstFetchItems() {
        mBucketModel.setId(mBucketView.getId());
        mBucketModel.setBucketType(mBucketView.getBucketType());
        super.firstFetchItems();
    }
}
