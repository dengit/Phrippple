package com.dengit.phrippple.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.api.DribbbleAPIHelper;
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

public class MainActivity extends BaseActivity<Shot> implements MainView<Shot>,  AdapterView.OnItemClickListener {

    @Inject
    MainPresenter<Shot> mMainPresenter;
//    MainPresenter<Shot> mMainPresenter;
    private ShotsAdapter mShotsAdapter;

//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;

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
        setupToolbar();
        setupComponent();
//        mMainPresenter = new MainPresenterImpl<>(this);
        setBasePresenter(mMainPresenter);
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>());
        mListView.setAdapter(mShotsAdapter);
        mListView.setOnItemClickListener(this);

        initBase();

        tryToStartLoginActivity();
        EventBusUtil.getInstance().register(this);
    }

    private void setupToolbar() {
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitle(getTitle());
//        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setAdapterData(List<Shot> newItems) {
        mShotsAdapter.setData(newItems);
    }

    @Override
    protected void appendAdapterData(List<Shot> newItems) {
        mShotsAdapter.appendData(newItems);
    }


    private void tryToStartLoginActivity() {
        if (!DribbbleAPIHelper.getInstance().hasAccessToken()) {
            startActivity(AuthorizeActivity.createIntent());
        } else {
            mMainPresenter.firstFetchItems();
        }
    }

    @Subscribe
    public void firstFetchShots(TokenInfo tokenInfo) {
        DribbbleAPIHelper.getInstance().setAccessTokenInfo(tokenInfo);
        mMainPresenter.firstFetchItems();
    }

    @Subscribe
    public void requestToken(AuthorizeInfo info) {
        mMainPresenter.requestToken(info);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ShotActivity.createIntent((Shot) mShotsAdapter.getItem(position)));
    }
}
