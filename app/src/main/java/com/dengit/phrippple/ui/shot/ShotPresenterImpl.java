package com.dengit.phrippple.ui.shot;

import android.content.Intent;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotPresenterImpl implements ShotPresenter {

    private ShotView mShotView;
    private ShotModel mShotModel;

    public ShotPresenterImpl(ShotView shotView) {
        mShotView = shotView;
        mShotModel = new ShotModelImpl();
    }
}
