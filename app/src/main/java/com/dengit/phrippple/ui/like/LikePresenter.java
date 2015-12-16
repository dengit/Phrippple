package com.dengit.phrippple.ui.like;

import com.dengit.phrippple.data.Shot;

import java.util.List;

/**
 * Created by dengit on 15/12/14.
 */
public interface LikePresenter {
    void onFooterClick();

    void onFirstFetchShots();

    void fetchNewestShots(boolean isRefresh);

    void onLoadNewestFinished(List<Shot> newShots);

    void onLoadMoreFinished(List<Shot> newShots);

    void onError();
}
