package com.dengit.phrippple.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Bind(R.id.user_other_info_list)
    ViewGroup mOtherInfoLayout;

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
        mUserPortrait.setImageURI(Uri.parse(mUser.avatar_url));
        mUserName.setText(mUser.name);
        mUserLocation.setText(mUser.location);
        mUserBio.setText(mUser.bio);
        flatOtherInfo(mUser);
    }

    private void flatOtherInfo(User mUser) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int padding = getResources().getDimensionPixelSize(R.dimen.text_padding);

        if (mUser.links != null && !TextUtils.isEmpty(mUser.links.twitter)) {
            addTextViewToLayout(mOtherInfoLayout, mUser.links.twitter, params, padding);
        }

        if (mUser.shots_count > 0) {
            addTextViewToLayout(mOtherInfoLayout, mUser.shots_count + " shots", params, padding);
        }

        if (mUser.projects_count> 0) {
            addTextViewToLayout(mOtherInfoLayout, mUser.projects_count + " projects", params, padding);
        }

        if (mUser.followers_count > 0) {
            addTextViewToLayout(mOtherInfoLayout, mUser.followers_count + " followers", params, padding);
        }

        if (mUser.followings_count> 0) {
            addTextViewToLayout(mOtherInfoLayout, mUser.followings_count + " followings", params, padding);
        }
        if (mUser.buckets_count> 0) {
            addTextViewToLayout(mOtherInfoLayout, mUser.buckets_count + " buckets", params, padding);
        }

        if (mUser.likes_count> 0) {
            addTextViewToLayout(mOtherInfoLayout, mUser.likes_count + " likes", params, padding);
        }

        if (mUser.teams_count> 0) {
            addTextViewToLayout(mOtherInfoLayout, mUser.teams_count + " teams", params, padding);
        }
    }

    private void addTextViewToLayout(ViewGroup headerLayout, String text, LinearLayout.LayoutParams params, int padding) {
        TextView textView = new TextView(this);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(text);
        headerLayout.addView(textView, params);
    }


    public static Intent createIntent(User user) {
        Intent intent = new Intent(APP.getInstance(), ProfileActivity.class);
        intent.putExtra("user", user);
        return intent;
    }
}
