package com.dengit.phrippple.ui.shot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.comment.CommentActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.utils.Util;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotActivity extends BaseActivity implements ShotView {

    private Shot mShot;

    @Bind(R.id.shot_normal_image)
    SimpleDraweeView mShotNormalImage;

    @Bind(R.id.shot_author_portrait)
    SimpleDraweeView mAuthorPortrait;

    @Bind(R.id.shot_author_name)
    TextView mAuthorName;

    @Bind(R.id.shot_time)
    TextView mShotTime;

    @Bind(R.id.shot_liked)
    TextView mShotLiked;

    @Bind(R.id.shot_bucketed)
    TextView mShotBucketed;

    @Bind(R.id.shot_view)
    TextView mShotView;

    @Bind(R.id.shot_commented)
    TextView mShotCommented;

    @Bind(R.id.shot_descrip)
    TextView mShotDescrip;

    private ShotPresenter mShotPresenter;

    public static Intent createIntent(Shot shot) {
        Intent intent = new Intent(APP.getInstance(), ShotActivity.class);
        intent.putExtra("shot", shot);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);
        ButterKnife.bind(this);

        initSetup();
    }

    private void initSetup() {
        mShotPresenter = new ShotPresenterImpl(this);
        mShot = (Shot) getIntent().getSerializableExtra("shot");
        setTitle(mShot.title);
        mShotNormalImage.setImageURI(Uri.parse(mShot.images.normal));
        mAuthorPortrait.setImageURI(Uri.parse(mShot.user.avatar_url));
        mAuthorName.setText(mShot.user.name);
        mShotTime.setText(mShot.updated_at);
        mShotLiked.setText(mShot.likes_count + " likes");
        mShotBucketed.setText(mShot.buckets_count + " buckets");
        mShotView.setText(mShot.views_count + " views");
        mShotCommented.setText(mShot.comments_count + " comments");

        mShotDescrip.setText(Util.textToHtml(mShot.description));
    }

    @Override
    public void onStartActivity(Intent intent) {
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.shot_commented)
    public void onClickCommented(View v) {
        startActivity(CommentActivity.createIntent(mShot.id, mShot.comments_count));
    }

    @OnClick(R.id.shot_author_portrait)
    public void onClickAuthorPortrait(View v) {
        startActivity(ProfileActivity.createIntent(mShot.user));
    }

    @OnClick(R.id.shot_author_name)
    public void onClickAuthorName(View v) {
        startActivity(ProfileActivity.createIntent(mShot.user));
    }

    @OnClick(R.id.shot_liked)
    public void onClickShotLiked(View v) {
//        startActivity(LikedActivity.createIntent(mShot.user.id, mShot.user.likes_count));
    }

    @OnClick(R.id.shot_bucketed)
    public void onClickShotBucketed(View v) {
//        startActivity(BuckedtActivity.createIntent(mShot.user.id, mShot.buckets_count));
    }
}
