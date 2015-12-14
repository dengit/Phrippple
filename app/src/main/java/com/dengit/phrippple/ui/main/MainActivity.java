package com.dengit.phrippple.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.TokenInfo;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.login.AuthorizeActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainView, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnClickListener {

    @Inject
    MainPresenter mMainPresenter;

    @Bind(R.id.listview_main)
    ListView mListView;

    @Bind(R.id.main_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private ShotsAdapter mShotsAdapter;
    private View mFooter;
    private ProgressBar mFooterProgressBar;
    private TextView mLoadMoreTV;
    private View mFooterLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initSetup();

        Intent intent = getIntent();
        Timber.d(intent.toString());

    }

    private void initSetup() {
        setupComponent();
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>());
        mListView.setAdapter(mShotsAdapter);
        mListView.setOnItemClickListener(this);

        mFooterLayout = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
        mFooter = mFooterLayout.findViewById(R.id.footer);
        mFooterProgressBar = (ProgressBar) mFooterLayout.findViewById(R.id.footer_progressbar);
        mLoadMoreTV = (TextView) mFooterLayout.findViewById(R.id.footer_loadmore);

        mFooter.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);

        startLoginActivity();
        EventBusUtil.getInstance().register(this);
    }

    private void setupComponent() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.getInstance().unregister(this);
    }

    @Override
    public void switchLoadMore(boolean more) {
        if (more) {
            mFooter.setClickable(false);
            mFooterProgressBar.setVisibility(View.VISIBLE);
            mLoadMoreTV.setText("loading");
        } else {
            mFooter.setClickable(true);
            mFooterProgressBar.setVisibility(View.GONE);
            mLoadMoreTV.setText("click to load more");
        }
    }

    @Override
    public void setItems(List<Shot> newShots) {
        mShotsAdapter.setData(newShots);
        mListView.removeFooterView(mFooterLayout); //todo add progressbar
        mListView.addFooterView(mFooterLayout);
    }

    @Override
    public void appendItems(List<Shot> newShots) {
        mShotsAdapter.appendData(newShots);
    }

    @Override
    public void switchRefresh(boolean refresh) {
        mRefreshLayout.setRefreshing(refresh);
    }

    private void startLoginActivity() {
        startActivity(AuthorizeActivity.createIntent());
    }


    @Subscribe
    public void onFirstFetchShots(TokenInfo tokenInfo) {
        mMainPresenter.onFirstFetchShots(tokenInfo);
    }


    @Subscribe
    public void requestToken(AuthorizeInfo info) {
        mMainPresenter.requestToken(info);
    }


    @Override
    public void onRefresh() {
        mMainPresenter.fetchNewestShots(true);
    }

    @Override
    public void onClick(View v) {
        mMainPresenter.onFooterClick();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ShotActivity.createIntent((Shot) mShotsAdapter.getItem(position)));
    }
}
