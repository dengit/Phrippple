package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.ui.base.FetchBasePresenter;

/**
 * Created by dengit on 15/12/14.
 */
public interface BucketPresenter extends FetchBasePresenter<Bucket> {
    void attachView(BucketView bucketView);

    void detachView();
}
