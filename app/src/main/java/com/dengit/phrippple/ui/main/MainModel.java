package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.TokenInfo;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainModel {

    void loadMore();

    boolean checkIfCanRefresh();

    void loadNewest();

    void requestToken(AuthorizeInfo info);

    void setToken(TokenInfo tokenInfo);
}
