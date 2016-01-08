package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketPresenterImpl extends FetchBasePresenterImpl<Bucket> implements BucketPresenter {
    private BucketView mBucketView;
    private BucketModel mBucketModel;

    public BucketPresenterImpl() {
        mBucketModel = new BucketModelImpl(this);
        attachBaseModel(mBucketModel);
    }

    @Override
    public void firstFetchItems() {
        checkAttached();
        mBucketModel.setId(mBucketView.getId());
        mBucketModel.setBucketType(mBucketView.getBucketType());
        super.firstFetchItems();
    }

    @Override
    public void attachView(BucketView bucketView) {
        super.attachBaseView(bucketView);
        mBucketView = bucketView;
    }

    public boolean isViewAttached() {
        return mBucketView != null;
    }

    public void checkAttached() {
        if (!isViewAttached()) throw new RuntimeException(
                "Please call "+this.getClass().getSimpleName()+".attachView() before " +
                        "requesting data to the Presenter");
    }
}
