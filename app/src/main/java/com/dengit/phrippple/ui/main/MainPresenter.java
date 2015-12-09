package com.dengit.phrippple.ui.main;

import android.view.View;

import com.dengit.phrippple.model.AuthorizeInfo;
import com.dengit.phrippple.model.Shot;
import com.dengit.phrippple.model.TokenInfo;

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
