package com.dengit.phrippple.ui.bucket;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.data.LikeShot;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.utils.EventBusUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketModelImpl implements BucketModel {

    private DribbbleAPI mDribbbleAPI;
    private String mAccessToken;

    public BucketModelImpl() {
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
        mAccessToken = DribbbleAPIHelper.getInstance().getAccessToken();
    }

    @Override
    public void fetchBuckets(int userId) {
        final ArrayList<Bucket> newItems = new ArrayList<>();
        mDribbbleAPI.getBuckets(userId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bucket>>() {
                    @Override
                    public void onCompleted() {
                        EventBusUtil.getInstance().post(newItems); //todo may conflict with main activity as same type
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Bucket> buckets) {
                        newItems.addAll(buckets);
                    }
                });

    }
}
