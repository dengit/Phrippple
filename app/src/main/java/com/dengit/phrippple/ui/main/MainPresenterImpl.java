package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/9.
 */
public class MainPresenterImpl extends FetchBasePresenterImpl<Shot> implements MainPresenter {
    private MainView mMainView;
    private MainModel mMainModel;

    public MainPresenterImpl() {
        mMainModel = new MainModelImpl(this); //todo use DI?
        attachBaseModel(mMainModel);
    }

    @Override
    public void requestToken(AuthorizeInfo info) {
        checkAttached();
        mMainModel.requestToken(info);
    }

    @Override
    public void firstFetchItems() {
        checkAttached();
        mMainModel.setCurrSort(mMainView.getCurrSort());
        mMainModel.setCurrList(mMainView.getCurrList());
        mMainModel.setCurrTimeFrame(mMainView.getCurrTimeFrame());
        super.firstFetchItems();
    }

    @Override
    public void fetchUserInfo() {
        checkAttached();
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


    @Override
    public void attachView(MainView mainView) {
        attachBaseView(mainView);
        mMainView = mainView;
    }

    public boolean isViewAttached() {
        return mMainView != null;
    }

    public void checkAttached() {
        if (!isViewAttached()) throw new RuntimeException(
                "Please call "+this.getClass().getSimpleName()+".attachView() before " +
                        "requesting data to the Presenter");
    }
}
