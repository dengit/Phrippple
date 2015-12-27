package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.BaseView;

import java.util.List;

/**
 * Created by dengit on 15/12/9.
 */
public interface MainView<T> extends BaseView<T> {
    String getCurrTimeFrame();

    String getCurrSort();

    String getCurrList();
}
