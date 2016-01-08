package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.base.FetchBaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotListModel extends FetchBaseModel<Shot> {
    void setId(int userId);

    void setShotListType(ShotListType shotListType);
}
