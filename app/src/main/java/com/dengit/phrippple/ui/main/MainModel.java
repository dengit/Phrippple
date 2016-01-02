package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.ui.base.BaseModel;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainModel<T> extends BaseModel<T> {
    void requestToken(AuthorizeInfo info);

    void setCurrSort(String currSort);

    void setCurrList(String currList);

    void setCurrTimeFrame(String currTimeFrame);

    void fetchUserInfo();
}
