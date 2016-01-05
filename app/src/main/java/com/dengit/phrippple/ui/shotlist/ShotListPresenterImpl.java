package com.dengit.phrippple.ui.shotlist;

import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotListPresenterImpl<T> extends FetchBasePresenterImpl<T> implements ShotListPresenter<T> {
    private ShotListView<T> mShotListView;
    private ShotListModel<T> mShotListModel;

    public ShotListPresenterImpl(ShotListView<T> shotListView) {
        super(shotListView);
        mShotListView = shotListView;
        mShotListModel = new ShotListModelImpl<T>(this);
        setBaseModel(mShotListModel);
    }

    @Override
    public void firstFetchItems() {
        mShotListModel.setId(mShotListView.getId());
        mShotListModel.setShotListType(mShotListView.getShotListType());
        super.firstFetchItems();
    }


}
