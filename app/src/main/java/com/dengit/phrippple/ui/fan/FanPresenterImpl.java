package com.dengit.phrippple.ui.fan;

import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class FanPresenterImpl extends FetchBasePresenterImpl<Fan> implements FanPresenter {
    private FanView mFanView;
    private FanModel mFanModel;

    public FanPresenterImpl(FanView fanView) {
        super(fanView);
        mFanView = fanView;
        mFanModel = new FanModelImpl(this);
        setBaseModel(mFanModel);
    }

    @Override
    public void firstFetchItems() {
        mFanModel.setShotId(mFanView.getShotId());
        super.firstFetchItems();
    }
}
