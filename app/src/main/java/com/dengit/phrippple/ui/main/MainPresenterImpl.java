package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.base.BasePresenterImpl;
import com.dengit.phrippple.ui.base.BaseView;

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
    public void detachView() {
    }

    @Override
    public void attachView(BaseView<T> view) {
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

    @Override
    public void fetchUserInfo() {
        mMainModel.fetchUserInfo();
    }

    @Override
    public void onFetchUserInfoFinished(User userInfo) {
        mMainView.onFetchUserInfoFinished(userInfo);
    }

    @Override
    public void onFetchUserInfoError() {
        mMainView.onFetchUserInfoError();
    }
}
