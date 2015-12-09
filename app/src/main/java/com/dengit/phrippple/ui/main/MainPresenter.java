package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.TokenInfo;

import java.util.List;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainPresenter {
    void onFooterClick();

    void onRefreshFinished(List<Shot> newShots);

    void onLoadMoreFinished(List<Shot> newShots);

    void onError();

    void requestToken(AuthorizeInfo info);

    void fetchNewestShots(boolean isRefresh);

    void onFirstFetchShots(TokenInfo tokenInfo);
}
