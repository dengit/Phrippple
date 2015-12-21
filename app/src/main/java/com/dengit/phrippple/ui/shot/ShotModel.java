package com.dengit.phrippple.ui.shot;

import com.dengit.phrippple.ui.BaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotModel {
    void setShotId(int shotId);

    void checkLikeStatus();

    void setLikeStatus(boolean status);
}
