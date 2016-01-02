package com.dengit.phrippple.ui.fan;

import com.dengit.phrippple.ui.base.BasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class FanPresenterImpl<T> extends BasePresenterImpl<T> implements FanPresenter<T> {
    private FanView<T> mFanView;
    private FanModel<T> mFanModel;

    public FanPresenterImpl(FanView<T> fanView) {
        super(fanView);
        mFanView = fanView;
        mFanModel = new FanModelImpl<T>(this);
        setBaseModel(mFanModel);
    }

    @Override
    public void firstFetchItems() {
        mFanModel.setShotId(mFanView.getShotId());
        super.firstFetchItems();
    }
}
