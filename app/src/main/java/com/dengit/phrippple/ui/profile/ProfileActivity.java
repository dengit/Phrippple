package com.dengit.phrippple.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.bucket.BucketActivity;
import com.dengit.phrippple.ui.like.LikeActivity;
import com.dengit.phrippple.utils.Util;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengit on 15/12/14.
 */
public class ProfileActivity extends BaseActivity {

    @Bind(R.id.user_portrait)
    SimpleDraweeView mUserPortrait;

    @Bind(R.id.user_name)
    TextView mUserName;

    @Bind(R.id.user_location)
    TextView mUserLocation;

    @Bind(R.id.user_bio)
    TextView mUserBio;

    @Bind(R.id.user_link_web)
    TextView mUserLinkWeb;

    @Bind(R.id.user_link_twitter)
    TextView mUserLinkTwitter;

    @Bind(R.id.user_buckets_count)
    TextView mUserBucketsCount;

    @Bind(R.id.user_followers_count)
    TextView mUserFollowersCount;

    @Bind(R.id.user_followings_count)
    TextView mUserFollowingsCount;

    @Bind(R.id.user_likes_count)
    TextView mUserLikesCount;

    @Bind(R.id.user_projects_count)
    TextView mUserProjectsCount;

    @Bind(R.id.user_shots_count)
    TextView mUserShotsCount;

    @Bind(R.id.user_created_at)
    TextView mUserCreateTime;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        initSetup();
    }

    private void initSetup() {
        mUser = (User) getIntent().getSerializableExtra("user");
        setTitle(mUser.name);
        mUserPortrait.setImageURI(Uri.parse(mUser.avatar_url));
        mUserName.setText(mUser.name);
        mUserLocation.setText(mUser.location);
        mUserBio.setText(Util.textToHtml(mUser.bio));
        visibleOtherInfo(mUser);
    }

    private void visibleOtherInfo(User mUser) {
        if (mUser.links != null && mUser.links.web != null && !TextUtils.isEmpty(mUser.links.web.trim())) {
            mUserLinkWeb.setVisibility(View.VISIBLE);
            mUserLinkWeb.setText("web " + mUser.links.web);
        }

        if (mUser.links != null && mUser.links.twitter != null &&  !TextUtils.isEmpty(mUser.links.twitter.trim())) {
            mUserLinkTwitter.setVisibility(View.VISIBLE);
            mUserLinkTwitter.setText("twitter " + mUser.links.twitter);
        }

        if (mUser.shots_count > 0) {
            mUserShotsCount.setVisibility(View.VISIBLE);
            mUserShotsCount.setText("shots " + mUser.shots_count);
        }

        if (mUser.projects_count > 0) {
            mUserProjectsCount.setVisibility(View.VISIBLE);
            mUserProjectsCount.setText("projects " + mUser.projects_count);
        }

        if (mUser.followers_count > 0) {
            mUserFollowersCount.setVisibility(View.VISIBLE);
            mUserFollowersCount.setText("followers " + mUser.followers_count);
        }

        if (mUser.followings_count > 0) {
            mUserFollowingsCount.setVisibility(View.VISIBLE);
            mUserFollowingsCount.setText("followings " + mUser.followings_count);
        }
        if (mUser.buckets_count > 0) {
            mUserBucketsCount.setVisibility(View.VISIBLE);
            mUserBucketsCount.setText("buckets " + mUser.buckets_count);
        }

        if (mUser.likes_count > 0) {
            mUserLikesCount.setVisibility(View.VISIBLE);
            mUserLikesCount.setText("likes " + mUser.likes_count);
        }
    }

    public static Intent createIntent(User user) {
        Intent intent = new Intent(APP.getInstance(), ProfileActivity.class);
        intent.putExtra("user", user);
        return intent;
    }

    @OnClick(R.id.user_buckets_count)
    public void onClickBucketsCount(View v) {
        startActivity(BucketActivity.createIntent(BucketType.Mine, mUser.id, mUser.buckets_count));
    }

    @OnClick(R.id.user_followers_count)
    public void onClickFollowersCount(View v) {
    }

    @OnClick(R.id.user_followings_count)
    public void onClickFollowingsCount(View v) {
    }

    @OnClick(R.id.user_likes_count)
    public void onClickLikesCount(View v) {
        startActivity(LikeActivity.createIntent(mUser.id, mUser.likes_count));
    }

    @OnClick(R.id.user_projects_count)
    public void onClickProjectsCount(View v) {
    }

    @OnClick(R.id.user_shots_count)
    public void onClickShotsCount(View v) {
    }
}
