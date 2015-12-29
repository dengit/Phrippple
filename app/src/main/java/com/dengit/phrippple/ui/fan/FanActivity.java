package com.dengit.phrippple.ui.fan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.FansAdapter;
import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.ui.TransitionBaseActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class FanActivity extends TransitionBaseActivity<Fan> implements FanView<Fan> {

    private int mShotId;
    private FansAdapter mFansAdapter;
    private FanPresenter<Fan> mFanPresenter;

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
        ButterKnife.bind(this);

        initSetup();
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

    private void initSetup() {
        mFanPresenter = new FanPresenterImpl<>(this);
        setBasePresenter(mFanPresenter);
        mShotId = getIntent().getIntExtra("shotId", 0);
        int fanCount = getIntent().getIntExtra("fanCount", 0);
        setTitle(fanCount + " fans");

        initBase();
        mFansAdapter = new FansAdapter(new ArrayList<Fan>(), mFooterLayout, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFansAdapter);
        mFanPresenter.firstFetchItems();
    }
}
