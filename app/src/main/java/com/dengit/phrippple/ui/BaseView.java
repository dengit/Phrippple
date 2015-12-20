package com.dengit.phrippple.ui;

import java.util.List;

/**
 * Created by dengit on 15/12/16.
 */
public interface BaseView<T> {

    void switchLoadMore(boolean isOpen, boolean isEnd);

    void setItems(List<T> newItems, boolean isEnd);

    void appendItems(List<T> newItems);

    void switchRefresh(boolean isOpen);

    void handleError();
}
