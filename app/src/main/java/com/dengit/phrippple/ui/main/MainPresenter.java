package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.TokenInfo;
import com.dengit.phrippple.ui.BasePresenter;

import java.util.List;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainPresenter<T> extends BasePresenter<T>{
    void requestToken(AuthorizeInfo info);
}
