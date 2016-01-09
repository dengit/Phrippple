package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.base.FetchBasePresenter;
import com.dengit.phrippple.ui.base.FetchBaseView;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainPresenter extends FetchBasePresenter<Shot> {

    void attachView(MainView mainView);

    void detachView();

    void requestToken(AuthorizeInfo info);

    void fetchUserInfo();

    void onFetchUserInfoFinished(User userInfo);

    void onFetchUserInfoError();
}
