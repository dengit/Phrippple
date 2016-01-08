package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.ui.base.FetchBaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface BucketModel extends FetchBaseModel<Bucket> {
    void setId(int id);

    void setBucketType(BucketType bucketType);
}
