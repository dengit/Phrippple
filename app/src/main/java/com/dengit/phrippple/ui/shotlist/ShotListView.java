package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.base.FetchBaseView;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotListView<T> extends FetchBaseView<T> {
    int getId();

    ShotListType getShotListType();
}
