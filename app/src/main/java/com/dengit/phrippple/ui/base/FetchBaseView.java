package com.dengit.phrippple.ui.base;

import java.util.List;

/**
 * Created by dengit on 15/12/16.
 */
public interface FetchBaseView<T> {

    void setItems(List<T> newItems);

    void appendItems(List<T> newItems);

    void switchRefresh(boolean isOpen);

    void switchLoading(boolean isLoading, boolean isEnd);

    void handleError();
}
