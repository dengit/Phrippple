package com.dengit.phrippple.ui.shot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotPresenter {
    void attachView(ShotView shotView);

    void checkLikeStatus();

    void setLikeStatus(boolean status);

    void updateLikeStatus();

    void fetchAco();

    void updateAco(List<String> shotColors);
}
