package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.base.BasePresenter;
import com.dengit.phrippple.ui.base.BaseView;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainPresenter<T> extends BasePresenter<T>{
    void attachView(BaseView<T> view);

    void detachView();

    void requestToken(AuthorizeInfo info);

    void fetchUserInfo();

    void onFetchUserInfoFinished(User userInfo);

    void onFetchUserInfoError();
}
