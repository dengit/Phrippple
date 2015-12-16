package com.dengit.phrippple.ui.like;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class LikeActivity extends BaseActivity implements LikeView, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnClickListener  {

    @Bind(R.id.like_list_view)
    ListView mLikeList;

    @Bind(R.id.like_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private int mUserId;
    private ShotsAdapter mShotsAdapter;
    private LikePresenter mLikePresenter;

    private View mFooter;
    private ProgressBar mFooterProgressBar;
    private TextView mLoadMoreTV;
    private View mFooterLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        ButterKnife.bind(this);

        initSetup();
    }

    @Override
    public int getUserId() {
        return mUserId;
    }

    private void initSetup() {
        mLikePresenter = new LikePresenterImpl(this);
        mUserId = getIntent().getIntExtra("userId", 0);
        int likeCount = getIntent().getIntExtra("likeCount", 0);
        setTitle(likeCount + " likes");
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>());
        mLikeList.setAdapter(mShotsAdapter);

        mFooterLayout = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
        mFooter = mFooterLayout.findViewById(R.id.footer);
        mFooterProgressBar = (ProgressBar) mFooterLayout.findViewById(R.id.footer_progressbar);
        mLoadMoreTV = (TextView) mFooterLayout.findViewById(R.id.footer_loadmore);

        mFooter.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);

        mLikePresenter.onFirstFetchShots();
    }

    public static Intent createIntent(int userId, int likeCount) {
        Intent intent = new Intent(APP.getInstance(), LikeActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("likeCount", likeCount);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void switchRefresh(boolean isOpen) {
        mRefreshLayout.setRefreshing(isOpen);
    }

    @Override
    public void switchLoadMore(boolean isOpen, boolean isEnd) {
        if (isOpen) {
            mFooter.setClickable(false);
            mFooterProgressBar.setVisibility(View.VISIBLE);
            mLoadMoreTV.setText("loading");
        } else {
            mFooterProgressBar.setVisibility(View.GONE);
            if (isEnd) {
                mFooter.setClickable(false);
                mLoadMoreTV.setText("no more data");
            } else {
                mFooter.setClickable(true);
                mLoadMoreTV.setText("click to load more");
            }
        }
    }

    @Override
    public void setItems(List<Shot> newShots) {
        mShotsAdapter.setData(newShots);
        mLikeList.removeFooterView(mFooterLayout); //todo add progressbar
        mLikeList.addFooterView(mFooterLayout);
    }

    @Override
    public void appendItems(List<Shot> newShots) {
        mShotsAdapter.appendData(newShots);
    }


    @Override
    public void onRefresh() {
        mLikePresenter.fetchNewestShots(true);
    }

    @Override
    public void onClick(View v) {
        mLikePresenter.onFooterClick();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ShotActivity.createIntent((Shot) mShotsAdapter.getItem(position)));
    }
}
