package com.dengit.phrippple.ui.fan;

import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class FanPresenterImpl extends FetchBasePresenterImpl<Fan> implements FanPresenter {
    private FanView mFanView;
    private FanModel mFanModel;

    public FanPresenterImpl() {
        mFanModel = new FanModelImpl(this);
        attachBaseModel(mFanModel);
    }

    @Override
    public void firstFetchItems() {
        checkAttached();
        mFanModel.setShotId(mFanView.getShotId());
        super.firstFetchItems();
    }

    @Override
    public void attachView(FanView fanView) {
        attachBaseView(fanView);
        mFanView = fanView;
    }

    @Override
    public void detachView() {
        detachBaseView();
        mFanView = null;
        mFanModel.onDetach();
    }

    public boolean isViewAttached() {
        return mFanView != null;
    }

    public void checkAttached() {
        if (!isViewAttached()) throw new RuntimeException(
                "Please call "+this.getClass().getSimpleName()+".attachView() before " +
                        "requesting data to the Presenter");
    }
}
