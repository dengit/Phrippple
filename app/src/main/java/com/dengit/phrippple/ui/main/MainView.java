package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.base.BaseView;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainView<T> extends BaseView<T> {
    String getCurrTimeFrame();

    String getCurrSort();

    String getCurrList();

    void onFetchUserInfoFinished(User userInfo);

    void onFetchUserInfoError();
}
