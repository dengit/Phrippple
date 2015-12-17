package com.dengit.phrippple.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dengit.phrippple.R;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/7.
 */
public abstract class BaseActivity<T> extends SuperBaseActivity implements BaseView<T>, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.list_view)
    protected ListView mListView;

    @Bind(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    protected View mFooter;
    protected ProgressBar mFooterProgressBar;
    protected TextView mLoadMoreTV;
    protected View mFooterLayout;

    private BasePresenter<T> mBasePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        mBasePresenter = new BasePresenterImpl<T>(this);
    }

    protected void setBasePresenter(BasePresenter<T> basePresenter) {
        mBasePresenter = basePresenter;
    }

    protected void initBase() {
        mFooterLayout = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
        mFooter = mFooterLayout.findViewById(R.id.footer);
        mFooterProgressBar = (ProgressBar) mFooterLayout.findViewById(R.id.footer_progressbar);
        mLoadMoreTV = (TextView) mFooterLayout.findViewById(R.id.footer_loadmore);

        mFooter.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        mBasePresenter.onFooterClick();
    }

    @Override
    public void switchRefresh(boolean isOpen) {
        mRefreshLayout.setRefreshing(isOpen);
    }

    @Override
    public void onRefresh() {
        mBasePresenter.fetchNewestItems(true);
    }

    @Override
    public void switchLoadMore(boolean isOpen, boolean isEnd) {
        if (isOpen) {
            mFooter.setClickable(false);
            mFooterProgressBar.setVisibility(View.VISIBLE);
            mLoadMoreTV.setText("loading");
        } else {
            setFooterStatus(isEnd);
        }
    }

    @Override
    public void setItems(List<T> newItems, boolean isEnd) {
        setAdapterData(newItems);

        if (mFooterLayout == null) {
            Timber.d("mFooterLayout == null");
            return;
        }
        setFooterStatus(isEnd);
        mListView.removeFooterView(mFooterLayout);
        mListView.addFooterView(mFooterLayout);
    }

    private void setFooterStatus(boolean isEnd) {
        mFooterProgressBar.setVisibility(View.GONE);
        if (isEnd) {
            mFooter.setClickable(false);
            mLoadMoreTV.setText("no more data");
        } else {
            mFooter.setClickable(true);
            mLoadMoreTV.setText("click to load more");
        }
    }

    protected abstract void setAdapterData(List<T> newItems);

    @Override
    public void appendItems(List<T> newItems) {
        appendAdapterData(newItems);
    }

    protected abstract void appendAdapterData(List<T> newItems);
}
