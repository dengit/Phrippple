package com.dengit.phrippple.ui.like;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class LikeActivity extends BaseActivity implements LikeView{

    @Bind(R.id.like_list_view)
    ListView mLikeList;

    private ShotsAdapter mShotsAdapter;
    private int mUserId;
    private LikePresenter mLikePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        ButterKnife.bind(this);

        initSetup();
    }

    private void initSetup() {
        mLikePresenter = new LikePresenterImpl(this);
        mUserId = getIntent().getIntExtra("userId", 0);
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>());
        mLikeList.setAdapter(mShotsAdapter);

        EventBusUtil.getInstance().register(this);

    }

    public static Intent createIntent(int userId) {
        Intent intent = new Intent(APP.getInstance(), LikeActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLikePresenter.onResume(mUserId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.getInstance().unregister(this);
    }


    @Subscribe
    public void appendData(LinkedList<Shot> shots) {
        mShotsAdapter.appendData(shots);
    }
}
