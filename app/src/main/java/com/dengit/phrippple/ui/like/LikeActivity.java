package com.dengit.phrippple.ui.like;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class LikeActivity extends BaseActivity<Shot> implements LikeView<Shot>, AdapterView.OnItemClickListener {

    private int mUserId;
    private ShotsAdapter mShotsAdapter;
    private LikePresenter<Shot> mLikePresenter;

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
        mLikePresenter = new LikePresenterImpl<>(this);
        setBasePresenter(mLikePresenter);
        mUserId = getIntent().getIntExtra("userId", 0);
        int likeCount = getIntent().getIntExtra("likeCount", 0);
        setTitle(likeCount + " likes");
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>());
        mListView.setAdapter(mShotsAdapter);
        mListView.setOnItemClickListener(this);

        initBase();
        mLikePresenter.firstFetchItems();
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
    public void setAdapterData(List<Shot> newItems) {
        mShotsAdapter.setData(newItems);
    }

    @Override
    public void appendAdapterData(List<Shot> newItems) {
        mShotsAdapter.appendData(newItems);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ShotActivity.createIntent((Shot) mShotsAdapter.getItem(position)));
    }
}
