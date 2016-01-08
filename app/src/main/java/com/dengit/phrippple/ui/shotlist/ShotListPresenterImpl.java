package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotListPresenterImpl extends FetchBasePresenterImpl<Shot> implements ShotListPresenter {
    private ShotListView mShotListView;
    private ShotListModel mShotListModel;

    public ShotListPresenterImpl(ShotListView shotListView) {
        super(shotListView);
        mShotListView = shotListView;
        mShotListModel = new ShotListModelImpl(this);
        setBaseModel(mShotListModel);
    }

    @Override
    public void firstFetchItems() {
        mShotListModel.setId(mShotListView.getId());
        mShotListModel.setShotListType(mShotListView.getShotListType());
        super.firstFetchItems();
    }


}
