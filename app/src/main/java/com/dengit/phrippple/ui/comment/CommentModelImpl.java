package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.Comment;
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
public class CommentModelImpl<T> extends BaseModelImpl<T> implements CommentModel<T> {

    private CommentPresenter<T> mPresenter;
    private int mShotId;

    public CommentModelImpl(CommentPresenter<T> presenter) {
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

        mDribbbleAPI.getComments(mShotId, page, DribbbleAPI.LIMIT_PER_PAGE, mAccessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comment>>() {
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
                    public void onNext(List<Comment> comments) {
                        newItems.addAll((List<T>)comments);
                    }
                });
    }
}