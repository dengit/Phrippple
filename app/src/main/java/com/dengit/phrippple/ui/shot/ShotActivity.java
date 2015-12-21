package com.dengit.phrippple.ui.shot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.data.Shot;
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
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotActivity extends SuperBaseActivity {

    private Shot mShot;

//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;

    @Bind(R.id.shot_normal_image)
    SimpleDraweeView mShotNormalImage;

    @Bind(R.id.shot_author_portrait)
    SimpleDraweeView mAuthorPortrait;

    @Bind(R.id.shot_author_name)
    TextView mAuthorName;

    @Bind(R.id.shot_time)
    TextView mShotTime;

    @Bind(R.id.shot_fan)
    TextView mShotLike;

    @Bind(R.id.shot_bucket)
    TextView mShotBucket;

    @Bind(R.id.shot_view)
    TextView mShotView;

    @Bind(R.id.shot_comment)
    TextView mShotCommented;

    @Bind(R.id.shot_descrip)
    TextView mShotDescrip;

    @Bind(R.id.shot_fab_menu)
    FloatingActionMenu mShotFabMenu;

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
        setupToolbar();
        setupFab();
        mShot = (Shot) getIntent().getSerializableExtra("shot");
        setTitle(mShot.title);
        tryToSetGifImage(mShotNormalImage, mShot);
        mAuthorPortrait.setImageURI(Uri.parse(mShot.user.avatar_url));
        mAuthorName.setText(mShot.user.name);
        mShotTime.setText(mShot.updated_at);
        mShotLike.setText(mShot.likes_count + " likes");
        mShotBucket.setText(mShot.buckets_count + " buckets");
        mShotView.setText(mShot.views_count + " views");
        mShotCommented.setText(mShot.comments_count + " comments");

        mShotDescrip.setText(Util.textToHtml(mShot.description));
    }

    private void setupFab() {
        mShotFabMenu.setClosedOnTouchOutside(true);
        mShotFabMenu.hideMenuButton(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mShotFabMenu.showMenuButton(true);
            }
        }, 400);

        FloatingActionButton sendCommentFab = (FloatingActionButton) findViewById(R.id.fab_send_comment);
        sendCommentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShotActivity.this, "send comment", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton likeFab = (FloatingActionButton) findViewById(R.id.fab_like);
        likeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShotActivity.this, "like this shot", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupToolbar() {
//        mToolbar.setTitle(getTitle());
//        setSupportActionBar(mToolbar);
//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
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
