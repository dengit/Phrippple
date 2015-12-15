package com.dengit.phrippple.ui.fan;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.utils.EventBusUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengit on 15/12/14.
 */
public class FanModelImpl implements FanModel {

    private DribbbleAPI mDribbbleAPI;
    private String mAccessToken;

    public FanModelImpl() {
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
        mAccessToken = DribbbleAPIHelper.getInstance().getAccessToken();
    }

    @Override
    public void fetchFans(int shotId) {
        final ArrayList<Fan> newItems = new ArrayList<>();
        mDribbbleAPI.getFans(shotId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Fan>>() {
                    @Override
                    public void onCompleted() {
                        EventBusUtil.getInstance().post(newItems); //todo may conflict with main activity as same type
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Fan> fans) {
                            newItems.addAll(fans);
                    }
                });

    }
}
