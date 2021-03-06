package com.dengit.phrippple.ui.shotlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.injection.component.DaggerActivityComponent;
import com.dengit.phrippple.ui.base.transition.BaseTransitionFetchActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotListActivity extends BaseTransitionFetchActivity<Shot> implements ShotListView, ShotsAdapter.OnShotItemClickListener {

    @Inject
    ShotsAdapter mShotsAdapter;

    @Inject
    ShotListPresenter mShotListPresenter;

    private int mId;
    private int mCount;
    private ShotListType mShotListType;

    public static Intent createIntent(Bundle args) {
        Intent intent = new Intent(APP.getInstance(), ShotListActivity.class);
        intent.putExtra("args", args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shotlist);
        getActivityComponent().inject(this);
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

    @Override
    public void onHeaderClick(View view, int position) {
        startProfileDetailActivity(view, position);
    }

    @Override
    public void onItemClick(View view, int position) {
        startShotDetailActivity(view, position);
    }

    private void startShotDetailActivity(View view, int position) {
        Shot shot = (Shot) mShotsAdapter.getItem(position);
        setUser(shot); //shot.user is null when shots are users'
        final Intent intent = ShotActivity.createIntent(shot);
        startDetailActivity(view, intent, R.id.shot_item_image);
    }

    private void startProfileDetailActivity(View view, int position) {
        Shot shot = (Shot) mShotsAdapter.getItem(position);
        final Intent intent = ProfileActivity.createIntent(shot.user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startDetailActivity(view, intent, R.id.shot_item_author_image);
    }

    private void initSetup() {
        mShotListPresenter.attachView(this);
        setupBase(mShotListPresenter);
        parseArgs();
        setupTitle();
        setupRecyclerView();
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

    private void setupTitle() {
        if (mShotListType == ShotListType.ShotsOfFollowing) {
            setTitle("Following shots");
        } else {
            setTitle(mCount + " shots");
        }
    }

    private void setupRecyclerView() {
        mShotsAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mShotsAdapter);
    }

    private User getUser() {
        Bundle args = getIntent().getBundleExtra("args");
        mShotListType = (ShotListType) args.getSerializable("type");
        if (mShotListType == ShotListType.ShotsOfSelf) {
            return (User) args.getSerializable("user");
        }

        return null;
    }

    private void setUser(Shot shot) {
        if (getUser() != null && shot.user == null) {
            shot.user = getUser();
        }
    }

}
