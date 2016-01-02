package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.ui.base.BaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface BucketModel<T> extends BaseModel<T> {
    void setId(int id);

    void setBucketType(BucketType bucketType);
}
