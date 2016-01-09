package com.dengit.phrippple.ui.fan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.FansAdapter;
import com.dengit.phrippple.adapter.RecyclerViewAdapter;
import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.ui.base.transition.BaseTransitionFetchActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class FanActivity extends BaseTransitionFetchActivity<Fan> implements FanView, RecyclerViewAdapter.OnItemClickListener {
    @Inject
    FansAdapter mFansAdapter;

    @Inject
    FanPresenter mFanPresenter;

    private int mShotId;

    public static Intent createIntent(int shotId, int fanCount) {
        Intent intent = new Intent(APP.getInstance(), FanActivity.class);
        intent.putExtra("shotId", shotId);
        intent.putExtra("fanCount", fanCount);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        initSetup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFanPresenter.detachView();
    }

    @Override
    public int getShotId() {
        return mShotId;
    }

    @Override
    protected void appendAdapterData(List<Fan> newItems) {
        mFansAdapter.appendData(newItems);
    }

    @Override
    protected void setAdapterData(List<Fan> newItems) {
        mFansAdapter.setData(newItems);
    }

    @Override
    public void onItemClick(View view, int position) {
        startProfileDetailActivity(view, position);
    }

    private void startProfileDetailActivity(View view, int position) {
        Fan fan = (Fan) mFansAdapter.getItem(position);
        final Intent intent = ProfileActivity.createIntent(fan.user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startDetailActivity(view, intent, R.id.fan_portrait_image);
    }

    private void initSetup() {
        mFanPresenter.attachView(this);
        setupBase(mFanPresenter);

        mShotId = getIntent().getIntExtra("shotId", 0);
        int fanCount = getIntent().getIntExtra("fanCount", 0);
        setTitle(fanCount + " fans");

        mFansAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mFansAdapter);

        mFanPresenter.firstFetchItems();
    }
}
