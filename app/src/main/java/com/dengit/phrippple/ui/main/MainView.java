package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.Shot;

import java.util.List;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainView {
    void switchLoadMore(boolean more);

    void setItems(List<Shot> newShots);

    void appendItems(List<Shot> newShots);

    void switchRefresh(boolean refresh);
}
