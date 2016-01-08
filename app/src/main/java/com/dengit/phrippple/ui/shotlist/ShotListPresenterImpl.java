package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotListPresenterImpl extends FetchBasePresenterImpl<Shot> implements ShotListPresenter {
    private ShotListView mShotListView;
    private ShotListModel mShotListModel;

    public ShotListPresenterImpl() {
        mShotListModel = new ShotListModelImpl(this);
        attachBaseModel(mShotListModel);
    }

    @Override
    public void attachView(ShotListView shotListView) {
        super.attachBaseView(shotListView);
        mShotListView = shotListView;
    }


    @Override
    public void firstFetchItems() {
        checkAttached();
        mShotListModel.setId(mShotListView.getId());
        mShotListModel.setShotListType(mShotListView.getShotListType());
        super.firstFetchItems();
    }

    public boolean isViewAttached() {
        return mShotListView != null;
    }

    public void checkAttached() {
        if (!isViewAttached()) throw new RuntimeException(
                "Please call "+this.getClass().getSimpleName()+".attachView() before " +
                        "requesting data to the Presenter");
    }
}
