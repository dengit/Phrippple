package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.ui.base.FetchBaseView;

/**
 * Created by dengit on 15/12/14.
 */
public interface BucketView<T> extends FetchBaseView<T> {
    int getId();

    BucketType getBucketType();
}
