package com.dengit.phrippple.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.SuperBaseActivity;
import com.dengit.phrippple.ui.bucket.BucketActivity;
import com.dengit.phrippple.ui.shotlist.ShotListActivity;
import com.dengit.phrippple.utils.Util;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class ProfileActivity extends SuperBaseActivity {

    private static final String DEFAULT_HEADER_IMAGE = "https://d13yacurqjgara.cloudfront.net/users/995516/avatars/normal/195f0357fb7fca71c46f4d3e1a733a5f.jpg?1447156390";
    @Bind(R.id.header_layout)
    View mProfileHeader;

    @Bind(R.id.header_blur_image)
    SimpleDraweeView mHeaderBlur;

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

    public static Intent createIntent(User user) {
        Intent intent = new Intent(APP.getInstance(), ProfileActivity.class);
        intent.putExtra("user", user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        initSetup();
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

        Bundle args = new Bundle();
        args.putInt("id", mUser.id);
        args.putSerializable("type", ShotListType.ShotsOfLikes);
        args.putInt("count", mUser.likes_count);

        startActivity(ShotListActivity.createIntent(args));
    }

    @OnClick(R.id.user_projects_count)
    public void onClickProjectsCount(View v) {
    }

    @OnClick(R.id.user_shots_count)
    public void onClickShotsCount(View v) {

        Bundle args = new Bundle();
        args.putSerializable("type", ShotListType.ShotsOfSelf);
        args.putSerializable("user", mUser);

        startActivity(ShotListActivity.createIntent(args));
    }

    private void initSetup() {
        mUser = (User) getIntent().getSerializableExtra("user");
        setTitle(mUser.name);
        setHeaderBlurImageURI(Uri.parse(mUser.avatar_url));
        setPortraitImageURI(Uri.parse(mUser.avatar_url));

        mUserName.setText(mUser.name);
        mUserLocation.setText(mUser.location);
        mUserBio.setText(Util.textToHtml(mUser.bio));
        visibleOtherInfo(mUser);
    }

    private void setPortraitImageURI(Uri uri) {

        ControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                //                Blurry.with(ProfileActivity.this)
                //                        .radius(10)
                //                        .sampling(8)
                //                        .async()
                //                        .capture(mUserPortrait)
                //                        .into(mHeaderBlur);
                Timber.d("**onFinalImageSet**");
            }

        };

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(mUserPortrait.getController())
                        .setControllerListener(listener)
                                // other setters as you need
                        .build();
        mUserPortrait.setController(controller);
    }

    private void setHeaderBlurImageURI(Uri uri) {

        if (mUser.avatar_url.contains(".gif")) {
            uri = Uri.parse(DEFAULT_HEADER_IMAGE);
        }

        Postprocessor blurPostprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "BlurPostprocessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                // >= level 17
                final RenderScript rs = RenderScript.create(ProfileActivity.this);
                final Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                        Allocation.USAGE_SCRIPT);
                final Allocation output = Allocation.createTyped(rs, input.getType());
                final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                script.setRadius(22);
                script.setInput(input);
                script.forEach(output);
                output.copyTo(bitmap);
            }
        };

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(blurPostprocessor)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(mHeaderBlur.getController())
                                // other setters as you need
                        .build();
        mHeaderBlur.setController(controller);
    }

    private void visibleOtherInfo(User mUser) {
        if (mUser.links != null && mUser.links.web != null && !TextUtils.isEmpty(mUser.links.web.trim())) {
            mUserLinkWeb.setVisibility(View.VISIBLE);
            mUserLinkWeb.setText("web " + mUser.links.web);
        }

        if (mUser.links != null && mUser.links.twitter != null && !TextUtils.isEmpty(mUser.links.twitter.trim())) {
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

}
