package com.dengit.phrippple.ui.base;

/**
 * Created by dengit on 15/12/16.
 */
public interface FetchBaseModel<T> {
    void loadMore();

    boolean checkIfCanRefresh();

    void loadNewest();
}
