package com.dengit.phrippple.ui.fan;

/**
 * Created by dengit on 15/12/14.
 */
public class FanPresenterImpl implements FanPresenter {
    private FanView mFanView;
    private FanModel mFanModel;

    public FanPresenterImpl(FanView fanView) {
        mFanView = fanView;
        mFanModel = new FanModelImpl();
    }

    @Override
    public void onResume(int userId) {
        mFanModel.fetchFans(userId);
    }
}
