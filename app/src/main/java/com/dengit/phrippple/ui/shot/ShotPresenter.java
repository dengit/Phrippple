package com.dengit.phrippple.ui.shot;

import com.dengit.phrippple.ui.BasePresenter;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotPresenter {
    void checkLikeStatus();

    void setLikeStatus(boolean status);

    void updateLikeStatus();
}
