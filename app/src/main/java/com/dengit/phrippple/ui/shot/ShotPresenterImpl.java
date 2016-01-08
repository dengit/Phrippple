package com.dengit.phrippple.ui.shot;

import java.util.List;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotPresenterImpl implements ShotPresenter {
    private ShotView mShotView;
    private ShotModel mShotModel;

    public ShotPresenterImpl() {
        mShotModel = new ShotModelImpl(this);
    }

    @Override
    public void checkLikeStatus() {
        checkAttached();
        mShotModel.setShotId(mShotView.getShotId());
        mShotModel.checkLikeStatus();
    }

    @Override
    public void setLikeStatus(boolean status) {
        mShotModel.setLikeStatus(status);
    }

    @Override
    public void updateLikeStatus() {
        mShotView.lightenLike();
    }

    @Override
    public void fetchAco() {
        checkAttached();
        mShotModel.setShotId(mShotView.getShotId());
        mShotModel.fetchAco();
    }

    @Override
    public void updateAco(List<String> shotColors) {
        mShotView.showAco(shotColors);
    }

    @Override
    public void attachView(ShotView shotView) {
        mShotView = shotView;
    }

    public boolean isViewAttached() {
        return mShotView != null;
    }

    public void checkAttached() {
        if (!isViewAttached()) throw new RuntimeException(
                "Please call "+this.getClass().getSimpleName()+".attachView() before " +
                        "requesting data to the Presenter");
    }
}
