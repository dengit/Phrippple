package com.dengit.phrippple.ui;

import com.dengit.phrippple.api.DribbbleAPI;

import java.util.List;

import timber.log.Timber;

/**
 * Created by dengit on 15/12/16.
 */
public class BasePresenterImpl<T> implements BasePresenter<T> {

    private BaseView<T> mBaseView;
    private BaseModel<T> mBaseModel;

    public BasePresenterImpl(BaseView<T> baseView) {
        mBaseView = baseView;
//        mBaseModel = new BaseModelImpl<T>(this); //todo use DI?
    }

    public void setBaseModel(BaseModel<T> baseModel) {
        mBaseModel = baseModel;
    }

    @Override
    public void onFooterClick() {
        mBaseView.switchLoadMore(true, false);
        mBaseModel.loadMore();
    }

    @Override
    public void onLoadNewestFinished(List<T> newItems) {
        mBaseView.switchRefresh(false);
        mBaseView.setItems(newItems);
    }

    @Override
    public void onLoadMoreFinished(List<T> newItems) {
        mBaseView.switchLoadMore(false, newItems.size() < DribbbleAPI.LIMIT_PER_PAGE);
        mBaseView.appendItems(newItems);
    }

    @Override
    public void onError() {
        mBaseView.switchRefresh(false);
    }

    @Override
    public void firstFetchItems() {
        fetchNewestItems(false);
    }

    @Override
    public void fetchNewestItems(boolean isRefresh) {
        if (isRefresh) {
            if (mBaseModel.checkIfCanRefresh()) {
                mBaseView.switchRefresh(true);
                mBaseModel.loadNewest();
            } else {
                Timber.d("** checkIfCanRefresh is false");
            }
        } else {
            mBaseModel.loadNewest();
        }
    }

}
