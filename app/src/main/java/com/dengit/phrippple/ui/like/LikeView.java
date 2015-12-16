package com.dengit.phrippple.ui.like;

import com.dengit.phrippple.data.Shot;

import java.util.List;

/**
 * Created by dengit on 15/12/14.
 */
public interface LikeView {
    int getUserId();

    void switchRefresh(boolean isOpen);

    void switchLoadMore(boolean isOpen, boolean isEnd);

    void setItems(List<Shot> newShots);

    void appendItems(List<Shot> newShots);
}
