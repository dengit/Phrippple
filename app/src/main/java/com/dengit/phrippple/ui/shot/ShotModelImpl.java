package com.dengit.phrippple.ui.shot;

import com.dengit.phrippple.RetryWithDelay;
import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.LikeShotResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotModelImpl implements ShotModel {

    private int mShotId;
    private ShotPresenter mPresenter;
    protected String mAccessToken;
    protected DribbbleAPI mDribbbleAPI;
    private CompositeSubscription mSubscriptions;

    public ShotModelImpl(ShotPresenter presenter) {
        mPresenter = presenter;
        mDribbbleAPI = DribbbleAPIHelper.getInstance().getDribbbleAPI();
        mAccessToken = DribbbleAPIHelper.getInstance().getAccessToken();
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void onDetach() {
        mSubscriptions.unsubscribe();
    }

    @Override
    public void setShotId(int shotId) {
        mShotId = shotId;
    }

    @Override
    public void checkLikeStatus() {
        mSubscriptions.add(mDribbbleAPI.checkLikeShot(mShotId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LikeShotResponse>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");
                        mPresenter.updateLikeStatus();
                    }

                    @Override
                    public void onError(Throwable e) {// if the user does not like the shot
                        Timber.d("**onError:%s", e.toString());
                    }

                    @Override
                    public void onNext(LikeShotResponse response) {
                        Timber.d("**response.id: %d", response.id);
                    }
                })
        );
    }

    @Override
    public void setLikeStatus(boolean status) {
        if (status) {
            doLikeShot();
        } else {
            undoLikeShot();
        }
    }

    private void doLikeShot() {
        mSubscriptions.add(mDribbbleAPI.likeShot(mShotId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LikeShotResponse>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {// if the user does not like the shot

                        Timber.d("**onError:%s", e.toString());
                    }

                    @Override
                    public void onNext(LikeShotResponse response) {
                        Timber.d("**response.id: %d", response.id);
                    }
                })
        );
    }

    private void undoLikeShot() {
        mSubscriptions.add(mDribbbleAPI.unlikeShot(mShotId, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**undoLikeShot onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {// if the user does not like the shot
                        Timber.d("**undoLikeShot onError:%s", e.toString());
                    }

                    @Override
                    public void onNext(Void response) {
                        Timber.d("**Void response onNext");
                    }
                })
        );
    }

    @Override
    public void fetchAco() {

        final ArrayList<String> shotColors = new ArrayList<>();
        mSubscriptions.add(Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                try {
                    byte[] acoBytes = DribbbleAPIHelper.getColorsAco(mShotId);
                    subscriber.onNext(parseAco(acoBytes));
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
        .retryWhen(new RetryWithDelay(5, 1000))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {
                Timber.d("shotColors: %s", shotColors);
                mPresenter.updateAco(shotColors);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<String> colors) {
                shotColors.addAll(colors);
            }
        })
        );
    }

    private List<String> parseAco(byte[] acoBytes) {
        if ((acoBytes == null) || (acoBytes.length < 5)) {
            return Collections.emptyList();
        }

        if (acoBytes[1] != 1) {
            return Collections.emptyList();
        }

        int colorCount = acoBytes[3];
        Timber.d("**colorCount: %d", colorCount);
        ArrayList<String> colors = new ArrayList<>();
        for (int i = 0, m = 3; (i < colorCount) && (m + 10 < acoBytes.length); ++i) {
            m += 4;
            int r = acoBytes[m];
            m += 2;
            int g = acoBytes[m];
            m += 2;
            int b = acoBytes[m];
            m += 2;
            String color = "#"
                    + String.format("%02x", r & 0xFF)
                    + String.format("%02x", g & 0xFF)
                    + String.format("%02x", b & 0xFF);
            colors.add(color);
        }

        return colors;
    }

}
