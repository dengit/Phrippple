package com.dengit.phrippple.ui.like;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
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
public class LikeModelImpl implements LikeModel {

    private DribbbleAPI mDribbbleAPI;
    private String mAccessToken;

    public LikeModelImpl() {
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
        mAccessToken = DribbbleAPIHelper.getInstance().getAccessToken();
    }

    @Override
    public void fetchLikeShots(int userId) {
        final LinkedList<Shot> tmpShots = new LinkedList<>();
        mDribbbleAPI.getLikeShots(userId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LikeShot>>() {
                    @Override
                    public void onCompleted() {
                        EventBusUtil.getInstance().post(tmpShots); //todo may conflict with main activity as same type
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<LikeShot> shots) {
                        for (LikeShot likeShot : shots) {
                            tmpShots.add(likeShot.shot);
                        }
                    }
                });

    }
}
