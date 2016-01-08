package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.base.FetchBaseView;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotListView extends FetchBaseView<Shot> {
    int getId();

    ShotListType getShotListType();
}
