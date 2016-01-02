package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.ui.base.BaseView;

/**
 * Created by dengit on 15/12/14.
 */
public interface BucketView<T> extends BaseView<T> {
    int getId();

    BucketType getBucketType();
}
