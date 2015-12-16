package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.ui.BaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface BucketModel<T> extends BaseModel<T> {
    void setBucketType(BucketType bucketType);

    void setId(int id);
}
