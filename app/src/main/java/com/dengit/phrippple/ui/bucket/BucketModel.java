package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.data.BucketType;

/**
 * Created by dengit on 15/12/14.
 */
public interface BucketModel {
    void fetchBuckets(BucketType bucketType, int id);
}
