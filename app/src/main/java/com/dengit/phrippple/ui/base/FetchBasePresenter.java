package com.dengit.phrippple.ui.base;

import java.util.List;

/**
 * Created by dengit on 15/12/16.
 */
public interface FetchBasePresenter<T>  {
    void attachBaseView(FetchBaseView<T> view);

    void detachBaseView();

    void attachBaseModel(FetchBaseModel<T> mBaseModel);

    void detachBaseModel();

    void onLoadMore();

    void onLoadNewestFinished(List<T> newItems);

    void onLoadMoreFinished(List<T> newItems);

    void onError();

    void firstFetchItems();

    void fetchNewestItems(boolean isRefresh);
}
