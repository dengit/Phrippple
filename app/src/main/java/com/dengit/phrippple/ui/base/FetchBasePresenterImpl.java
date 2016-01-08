package com.dengit.phrippple.ui.base;

import com.dengit.phrippple.api.DribbbleAPI;

import java.util.List;

import timber.log.Timber;

/**
 * Created by dengit on 15/12/16.
 */
public class FetchBasePresenterImpl<T> implements FetchBasePresenter<T> {

    private FetchBaseView<T> mFetchBaseView;
    private FetchBaseModel<T> mFetchBaseModel;

    public FetchBasePresenterImpl() {
    }

    @Override
    public void attachBaseView(FetchBaseView<T> fetchBaseView) {
        mFetchBaseView = fetchBaseView;
    }

    @Override
    public void detachBaseView() {
        mFetchBaseView = null;
    }


    @Override
    public void attachBaseModel(FetchBaseModel<T> mBaseModel) {
        mFetchBaseModel = mBaseModel;
    }

    @Override
    public void detachBaseModel() {
        mFetchBaseModel = null;
    }

    @Override
    public void onLoadMore() {
        checkBaseAttached();
        mFetchBaseModel.loadMore();
    }

    @Override
    public void onLoadNewestFinished(List<T> newItems) {
        mFetchBaseView.switchRefresh(false);
        mFetchBaseView.switchLoading(false, isEnd(newItems));
        mFetchBaseView.setItems(newItems);
    }

    @Override
    public void onLoadMoreFinished(List<T> newItems) {
        mFetchBaseView.switchLoading(false, isEnd(newItems));
        mFetchBaseView.appendItems(newItems);
    }

    @Override
    public void onError() {
        mFetchBaseView.handleError();
    }

    @Override
    public void firstFetchItems() {
        checkBaseAttached();
        fetchNewestItems(false);
    }

    @Override
    public void fetchNewestItems(boolean isRefresh) {
        checkBaseAttached();
        if (isRefresh) {
            if (mFetchBaseModel.checkIfCanRefresh()) {
                mFetchBaseView.switchRefresh(true);
                mFetchBaseModel.loadNewest();
            } else {
                Timber.d("** checkIfCanRefresh is false");
            }
        } else {
            mFetchBaseModel.loadNewest();
        }
    }

    private boolean isEnd(List<T> newItems) {
        return newItems.size() < DribbbleAPI.LIMIT_PER_PAGE;
    }

    public boolean isBaseViewAttached() {
        return mFetchBaseView != null;
    }
    public boolean isBaseModelAttached() {
        return mFetchBaseModel != null;
    }

    public void checkBaseAttached() {
        if (!isBaseViewAttached()) throw new RuntimeException(
                "Please call "+this.getClass().getSimpleName()+".attachBaseView() before " +
                        "requesting data to the Presenter");

        if (!isBaseModelAttached()) throw new RuntimeException(
                "Please call "+this.getClass().getSimpleName()+".attachBaseModel() before " +
                        "requesting data to the Presenter");
    }
}
