package com.dengit.phrippple.ui.base;

import java.util.List;

/**
 * Created by dengit on 15/12/16.
 */
public interface FetchBasePresenter<T>  {

    void onFooterClick();

    void onLoadNewestFinished(List<T> newItems);

    void onLoadMoreFinished(List<T> newItems);

    void onError();

    void firstFetchItems();

    void fetchNewestItems(boolean isRefresh);
}
