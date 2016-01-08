package com.dengit.phrippple.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dengit.phrippple.R;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/7.
 */
public abstract class FetchBaseActivity<T> extends BaseActivity implements FetchBaseView<T>, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.initial_progress_bar)
    protected ProgressWheel mInitialProgressBar;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    protected boolean mLoading = false;

    private FetchBasePresenter<T> mFetchBasePresenter;
    private LinearLayoutManager mLayoutManager;
    private boolean mEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        mBasePresenter = new BasePresenterImpl<T>(this);
        mLayoutManager = new LinearLayoutManager(this);
    }

    @Override
    public void switchRefresh(boolean isOpen) {
        mRefreshLayout.setRefreshing(isOpen);
    }

    @Override
    public void onRefresh() {
        mFetchBasePresenter.fetchNewestItems(true);
    }

    @Override
    public void handleError() {
        tryToGoneInitialProgressBar();
        switchRefresh(false);
        switchLoading(false, false);
        Toast.makeText(this, "network error! swipe to refresh it!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setItems(List<T> newItems) {
        tryToGoneInitialProgressBar();
        setAdapterData(newItems);
    }

    @Override
    public void switchLoading(boolean isLoading, boolean isEnd) {
        mLoading = isLoading;
        mEnd = isEnd;
    }

    @Override
    public void appendItems(List<T> newItems) {
        appendAdapterData(newItems);
    }

    protected void setBasePresenter(FetchBasePresenter<T> fetchBasePresenter) {
        mFetchBasePresenter = fetchBasePresenter;
    }

    protected abstract void setAdapterData(List<T> newItems);

    protected abstract void appendAdapterData(List<T> newItems);

    protected void setupBase() {
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                int totalCount = mLayoutManager.getItemCount();
                if (!mEnd &&!mLoading && lastVisibleItemPosition + 1 == totalCount) {
                    mLoading = true;
                    mFetchBasePresenter.onLoadMore();
                }
            }
        });

    }

    private void tryToGoneInitialProgressBar() {
        if (mInitialProgressBar.getVisibility() == View.VISIBLE) {
            mInitialProgressBar.setVisibility(View.GONE);
        }
    }

}
