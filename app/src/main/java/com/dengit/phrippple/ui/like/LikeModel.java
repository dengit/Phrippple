package com.dengit.phrippple.ui.like;

/**
 * Created by dengit on 15/12/14.
 */
public interface LikeModel {
    void loadMore();

    boolean checkIfCanRefresh();

    void loadNewest();

    void setUserId(int userId);
}
