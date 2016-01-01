package com.dengit.phrippple.ui.shotlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.TransitionBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotListActivity extends TransitionBaseActivity<Shot> implements ShotListView<Shot> {

    private int mId;
    private ShotListType mShotListType;
    private int mCount;
    private ShotsAdapter mShotsAdapter;
    private ShotListPresenter<Shot> mShotListPresenter;

    public static Intent createIntent(Bundle args) {
        Intent intent = new Intent(APP.getInstance(), ShotListActivity.class);
        intent.putExtra("args", args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shotlist);
        ButterKnife.bind(this);

        initSetup();
    }

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public ShotListType getShotListType() {
        return mShotListType;
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
    public void setAdapterData(List<Shot> newItems) {
        mShotsAdapter.setData(newItems);
    }

    @Override
    public void appendAdapterData(List<Shot> newItems) {
        mShotsAdapter.appendData(newItems);
    }

    private void initSetup() {
        parseArgs();
        mShotListPresenter = new ShotListPresenterImpl<>(this);
        setBasePresenter(mShotListPresenter);
        setTitle(mCount + " shots");

        initBase();
        mShotsAdapter = new ShotsAdapter(getUser(), new ArrayList<Shot>(), mFooterLayout, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mShotsAdapter);
        mShotListPresenter.firstFetchItems();
    }

    private void parseArgs() {
        User user = getUser();
        if (user != null) {
            mId = user.id;
            mCount = user.shots_count;
        } else {
            Bundle args = getIntent().getBundleExtra("args");
            mId = args.getInt("id", 0);
            mCount = args.getInt("count", 0);
        }
    }

    private User getUser() {
        Bundle args = getIntent().getBundleExtra("args");
        mShotListType = (ShotListType) args.getSerializable("type");
        if (mShotListType == ShotListType.ShotsOfSelf) {
            return (User) args.getSerializable("user");
        }

        return null;
    }

}
