package com.dengit.phrippple.ui.fan;

import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.ui.base.FetchBaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface FanModel extends FetchBaseModel<Fan> {
    void setShotId(int shotId);
}
