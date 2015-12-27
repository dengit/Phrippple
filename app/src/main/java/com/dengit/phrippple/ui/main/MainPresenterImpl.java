package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.ui.BasePresenterImpl;

/**
 * Created by dengit on 15/12/9.
 */
public class MainPresenterImpl<T> extends BasePresenterImpl<T> implements MainPresenter<T> {
    private MainView<T> mMainView;
    private MainModel<T> mMainModel;

    public MainPresenterImpl(MainView<T> mainView) {
        super(mainView);
        mMainView = mainView;
        mMainModel = new MainModelImpl<>(this); //todo use DI?
        setBaseModel(mMainModel);
    }

    @Override
    public void requestToken(AuthorizeInfo info) {
        mMainModel.requestToken(info);
    }

    @Override
    public void firstFetchItems() {
        mMainModel.setCurrSort(mMainView.getCurrSort());
        mMainModel.setCurrList(mMainView.getCurrList());
        mMainModel.setCurrTimeFrame(mMainView.getCurrTimeFrame());
        super.firstFetchItems();
    }
}
