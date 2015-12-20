package com.dengit.phrippple.ui.fan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.FansAdapter;
import com.dengit.phrippple.data.Fan;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class FanActivity extends BaseActivity<Fan> implements FanView<Fan>, AdapterView.OnItemClickListener {

    private int mShotId;
    private FansAdapter mFansAdapter;
    private FanPresenter<Fan> mFanPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        ButterKnife.bind(this);

        initSetup();
    }

    private void initSetup() {
        mFanPresenter = new FanPresenterImpl<>(this);
        setBasePresenter(mFanPresenter);
        mShotId = getIntent().getIntExtra("shotId", 0);
        int fanCount = getIntent().getIntExtra("fanCount", 0);
        setTitle(fanCount + " fans");
        mFansAdapter = new FansAdapter(new ArrayList<Fan>());
        mListView.setAdapter(mFansAdapter);
        mListView.setOnItemClickListener(this);

        initBase();
        mFanPresenter.firstFetchItems();
    }

    public static Intent createIntent(int shotId, int fanCount) {
        Intent intent = new Intent(APP.getInstance(), FanActivity.class);
        intent.putExtra("shotId", shotId);
        intent.putExtra("fanCount", fanCount);
        return intent;
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
    public int getShotId() {
        return mShotId;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ProfileActivity.createIntent((User) mFansAdapter.getItem(position)));
    }
}
