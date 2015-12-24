package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.BaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotListModel<T> extends BaseModel<T> {
    void setId(int userId);

    void setShotListType(ShotListType shotListType);
}
