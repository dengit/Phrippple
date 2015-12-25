package com.dengit.phrippple.ui.shotlist;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.TransitionBaseActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;
import com.dengit.phrippple.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class ShotListActivity extends TransitionBaseActivity<Shot> implements ShotListView<Shot>, AdapterView.OnItemClickListener {

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startDetailActivity(view, position);
    }

    private void initSetup() {
        parseArgs();
        mShotListPresenter = new ShotListPresenterImpl<>(this);
        setBasePresenter(mShotListPresenter);
        setTitle(mCount + " shots");
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>(), this);
        mListView.setAdapter(mShotsAdapter);
        mListView.setOnItemClickListener(this);

        initBase();
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

    private void setUser(Shot shot) {
        if (shot.user == null) {
            shot.user = getUser();
        }
    }

    /**
     * When the user clicks a thumbnail, bundle up information about it and launch the
     * details activity.
     */

    private void startDetailActivity(View view, int position) {
        final Intent intent = new Intent();
        intent.setClass(this, ShotActivity.class);

        Shot shot = (Shot) mShotsAdapter.getItem(position);
        setUser(shot); // in the case of ShotListType.ShotsOfSelf

        final SimpleDraweeView shotImage = (SimpleDraweeView) view.findViewById(R.id.shot_item_image);

        intent.putExtra("shot", shot);
        if (Utils.hasLollipop()) {
            startActivityLollipop(shotImage, intent, "photo_hero");
        } else {
            startActivityGingerBread(shotImage, intent);
        }
    }

}
