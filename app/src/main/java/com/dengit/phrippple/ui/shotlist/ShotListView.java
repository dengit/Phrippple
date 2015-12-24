package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.BaseView;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotListView<T> extends BaseView<T> {
    int getId();

    ShotListType getShotListType();
}
