package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.BasePresenter;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainPresenter<T> extends BasePresenter<T>{
    void requestToken(AuthorizeInfo info);

    void fetchUserInfo();

    void onFetchUserInfoFinished(User userInfo);

    void onFetchUserInfoError();
}
