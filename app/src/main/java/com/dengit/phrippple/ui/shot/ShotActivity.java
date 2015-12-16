package com.dengit.phrippple.ui.shot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.SuperBaseActivity;
import com.dengit.phrippple.ui.bucket.BucketActivity;
import com.dengit.phrippple.ui.comment.CommentActivity;
import com.dengit.phrippple.ui.fan.FanActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.utils.Util;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotActivity extends SuperBaseActivity {

    private Shot mShot;

    @Bind(R.id.shot_normal_image)
    SimpleDraweeView mShotNormalImage;

    @Bind(R.id.shot_author_portrait)
    SimpleDraweeView mAuthorPortrait;

    @Bind(R.id.shot_author_name)
    TextView mAuthorName;

    @Bind(R.id.shot_time)
    TextView mShotTime;

    @Bind(R.id.shot_fan)
    TextView mShotLiked;

    @Bind(R.id.shot_bucket)
    TextView mShotBucketed;

    @Bind(R.id.shot_view)
    TextView mShotView;

    @Bind(R.id.shot_comment)
    TextView mShotCommented;

    @Bind(R.id.shot_descrip)
    TextView mShotDescrip;

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
        mShot = (Shot) getIntent().getSerializableExtra("shot");
        setTitle(mShot.title);
        tryToSetGifImage(mShotNormalImage, mShot);
        mAuthorPortrait.setImageURI(Uri.parse(mShot.user.avatar_url));
        mAuthorName.setText(mShot.user.name);
        mShotTime.setText(mShot.updated_at);
        mShotLiked.setText(mShot.likes_count + " likes");
        mShotBucketed.setText(mShot.buckets_count + " buckets");
        mShotView.setText(mShot.views_count + " views");
        mShotCommented.setText(mShot.comments_count + " comments");

        mShotDescrip.setText(Util.textToHtml(mShot.description));
    }

    private void tryToSetGifImage(SimpleDraweeView shotNormalImage, Shot shot) {
        GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(getResources())
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        String url = mShot.images.hidpi != null ? mShot.images.hidpi : mShot.images.normal;
        if (shot.animated) {
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(url))
                    .setAutoPlayAnimations(true)
                    .build();
            shotNormalImage.setController(controller);
            shotNormalImage.setHierarchy(gdh);
        } else {
            shotNormalImage.setImageURI(Uri.parse(url));
            shotNormalImage.setHierarchy(gdh);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.shot_comment)
    public void onClickShotComment(View v) {
        startActivity(CommentActivity.createIntent(mShot.id, mShot.comments_count));
    }

    @OnClick(R.id.shot_author_portrait)
    public void onClickShotAuthorPortrait(View v) {
        startActivity(ProfileActivity.createIntent(mShot.user));
    }

    @OnClick(R.id.shot_author_name)
    public void onClickShotAuthorName(View v) {
        startActivity(ProfileActivity.createIntent(mShot.user));
    }

    @OnClick(R.id.shot_fan)
    public void onClickShotFan(View v) {
        startActivity(FanActivity.createIntent(mShot.id, mShot.likes_count));
    }

    @OnClick(R.id.shot_bucket)
    public void onClickShotBucket(View v) {
        startActivity(BucketActivity.createIntent(BucketType.Others, mShot.id, mShot.buckets_count));
    }
}
