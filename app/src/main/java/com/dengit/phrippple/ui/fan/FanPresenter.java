package com.dengit.phrippple.ui.fan;

import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.ui.base.FetchBasePresenter;

/**
 * Created by dengit on 15/12/14.
 */
public interface FanPresenter extends FetchBasePresenter<Fan> {
    void attachView(FanView fanView);
}
