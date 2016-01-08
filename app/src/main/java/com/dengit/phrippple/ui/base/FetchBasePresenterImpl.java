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

    public FetchBasePresenterImpl(FetchBaseView<T> fetchBaseView) {
        mFetchBaseView = fetchBaseView;
//        mBaseModel = new BaseModelImpl<T>(this); //todo use DI?
    }

    @Override
    public void onLoadMore() {
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
        fetchNewestItems(false);
    }

    @Override
    public void fetchNewestItems(boolean isRefresh) {
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

    public void setBaseModel(FetchBaseModel<T> fetchBaseModel) {
        mFetchBaseModel = fetchBaseModel;
    }

    private boolean isEnd(List<T> newItems) {
        return newItems.size() < DribbbleAPI.LIMIT_PER_PAGE;
    }

}
