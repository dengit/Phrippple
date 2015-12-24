package com.dengit.phrippple.ui.shotlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotListActivity extends BaseActivity<Shot> implements ShotListView<Shot>, AdapterView.OnItemClickListener {

    private int mId;
    private ShotListType mShotListType;
    private ShotsAdapter mShotsAdapter;
    private ShotListPresenter<Shot> mShotListPresenter;

    public static Intent createIntent(int id, ShotListType type, int count) {
        Intent intent = new Intent(APP.getInstance(), ShotListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("count", count);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ShotActivity.createIntent((Shot) mShotsAdapter.getItem(position)));
    }

    private void initSetup() {
        mShotListPresenter = new ShotListPresenterImpl<>(this);
        setBasePresenter(mShotListPresenter);
        mId = getIntent().getIntExtra("id", 0);
        mShotListType = (ShotListType) getIntent().getSerializableExtra("type");
        int count = getIntent().getIntExtra("count", 0);
        setTitle(count + " shots");
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>());
        mListView.setAdapter(mShotsAdapter);
        mListView.setOnItemClickListener(this);

        initBase();
        mShotListPresenter.firstFetchItems();
    }

}
