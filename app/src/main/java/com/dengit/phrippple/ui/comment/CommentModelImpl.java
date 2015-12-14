package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.utils.EventBusUtil;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentModelImpl implements CommentModel {

    private DribbbleAPI mDribbbleAPI;
    private String mAccessToken;

    public CommentModelImpl() {
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
        mAccessToken = DribbbleAPIHelper.getInstance().getAccessToken();
    }

    @Override
    public void fetchShotComments(int shotId) {

        final ArrayList<Comment> tmpComments = new ArrayList<>();

        mDribbbleAPI.getComments(shotId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Comment>>() {
                    @Override
                    public void onCompleted() {
                        EventBusUtil.getInstance().post(tmpComments);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Comment> comments) {
                        tmpComments.addAll(comments);
                    }
                });
    }
}
