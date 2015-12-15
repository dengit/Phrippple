package com.dengit.phrippple.ui.fan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.FansAdapter;
import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class FanActivity extends BaseActivity implements FanView {

    @Bind(R.id.fan_list_view)
    ListView mFanList;

    private int mShotId;
    private FansAdapter mFansAdapter;
    private FanPresenter mFanPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        ButterKnife.bind(this);

        initSetup();
    }

    private void initSetup() {
        mFanPresenter = new FanPresenterImpl(this);
        mShotId = getIntent().getIntExtra("shotId", 0);
        int fanCount = getIntent().getIntExtra("fanCount", 0);
        setTitle(fanCount + " fans");
        mFansAdapter = new FansAdapter(new ArrayList<Fan>());
        mFanList.setAdapter(mFansAdapter);


    }

    public static Intent createIntent(int shotId, int fanCount) {
        Intent intent = new Intent(APP.getInstance(), FanActivity.class);
        intent.putExtra("shotId", shotId);
        intent.putExtra("fanCount", fanCount);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusUtil.getInstance().register(this);
        mFanPresenter.onResume(mShotId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusUtil.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Subscribe
    public void appendData(ArrayList<Fan> fans) {
        mFansAdapter.appendData(fans);
    }
}
