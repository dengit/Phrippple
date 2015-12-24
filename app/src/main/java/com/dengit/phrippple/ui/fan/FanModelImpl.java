package com.dengit.phrippple.ui.fan;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.ui.BaseModelImpl;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class FanModelImpl<T> extends BaseModelImpl<T> implements FanModel<T> {

    private int mShotId;
    private FanPresenter<T> mPresenter;

    public FanModelImpl(FanPresenter<T> presenter) {
        super(presenter);
        mPresenter = presenter;
    }

    @Override
    public void setShotId(int shotId) {
        mShotId = shotId;
    }

    @Override
    protected void fetchItems(final int page) {
        final ArrayList<T> newItems = new ArrayList<>();
        mDribbbleAPI.getFans(mShotId, page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Fan>>() {
                    @Override
                    public void onCompleted() { //todo use eventbus
                        Timber.d("**onCompleted");
                        mCurrPage = page;
                        if (page == 1) {
                            mPresenter.onLoadNewestFinished(newItems);
                        } else {
                            mPresenter.onLoadMoreFinished(newItems);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.d("**onError");
                        e.printStackTrace();
                        mPresenter.onError();
                    }

                    @Override
                    @SuppressWarnings("unchecked")
                    public void onNext(List<Fan> fans) {
                        Timber.d("**fans.size(): %d", fans.size());
                        newItems.addAll((List<T>) fans);
                    }
                });

    }
}
