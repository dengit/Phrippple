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
import com.dengit.phrippple.ui.bucket.BucketActivity;
import com.dengit.phrippple.ui.comment.CommentActivity;
import com.dengit.phrippple.ui.like.LikeActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.utils.Util;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotActivity extends BaseActivity implements ShotView, View.OnClickListener {

    private Shot mShot;

    @Bind(R.id.shot_normal_image)
    SimpleDraweeView mShotNormalImage;

    @Bind(R.id.shot_author_portrait)
    SimpleDraweeView mAuthorPortrait;

    @Bind(R.id.shot_author_name)
    TextView mAuthorName;

    @Bind(R.id.shot_time)
    TextView mShotTime;

    @Bind(R.id.shot_like)
    TextView mShotLike;

    @Bind(R.id.shot_bucket)
    TextView mShotBucket;

    @Bind(R.id.shot_view)
    TextView mShotView;

    @Bind(R.id.shot_comment)
    TextView mShotComment;

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
        mShotNormalImage.setImageURI(Uri.parse(mShot.images.normal));
        mAuthorPortrait.setImageURI(Uri.parse(mShot.user.avatar_url));
        mAuthorName.setText(mShot.user.name);
        mShotTime.setText(mShot.updated_at);
        mShotLike.setText(mShot.likes_count + " likes");
        mShotBucket.setText(mShot.buckets_count + " buckets");
        mShotView.setText(mShot.views_count + " views");
        mShotComment.setText(mShot.comments_count + " comments");

        mShotLike.setOnClickListener(this);
        mAuthorName.setOnClickListener(this);
        mAuthorPortrait.setOnClickListener(this);
        mShotComment.setOnClickListener(this);

        mShotDescrip.setText(Util.textToHtml(mShot.description));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v == mShotComment) {
            intent = CommentActivity.createIntent(mShot.id);
        } else if (v == mAuthorPortrait || v == mAuthorName) {
            intent = ProfileActivity.createIntent(mShot.user);
        } else if (v == mShotLike) {
            intent = LikeActivity.createIntent(mShot.user.id);
        } else if (v == mShotBucket) {
            intent = BucketActivity.createIntent(mShot.id);
        } else {
            Timber.d("** error at onClick");
            return;
        }

        mShotPresenter.startActivity(intent);
    }


    @Override
    public void onStartActivity(Intent intent) {
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
