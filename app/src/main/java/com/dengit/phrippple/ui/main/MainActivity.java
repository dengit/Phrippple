package com.dengit.phrippple.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.data.TokenInfo;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.injection.component.DaggerActivityComponent;
import com.dengit.phrippple.ui.base.transition.BaseTransitionFetchActivity;
import com.dengit.phrippple.ui.bucket.BucketActivity;
import com.dengit.phrippple.ui.login.AuthorizeActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.ui.shotlist.ShotListActivity;
import com.dengit.phrippple.util.EventBusUtil;
import com.dengit.phrippple.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseTransitionFetchActivity<Shot> implements MainView, View.OnClickListener,  AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.drawer_menu_listview)
    ListView mDrawerMenuListView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.spinner_sort)
    Spinner mSortSpinner;

    @Bind(R.id.spinner_list)
    Spinner mListSpinner;

    @Bind(R.id.spinner_time_frame)
    Spinner mTimeFrameSpinner;

    @Bind(R.id.fab_return_to_top)
    FloatingActionButton mReturnToTopFab;

    @Inject
    MainPresenter mMainPresenter;

    private ShotsAdapter mShotsAdapter;
    private boolean mIsSortSpinnerFirst = true;
    private boolean mIsListSpinnerFirst = true;
    private String[] mSortSpinnerArray;
    private String[] mListSpinnerArray;
    private String[] mTimeFrameSpinnerArray;
    private String mCurrTimeFrame;
    private String mCurrSort;
    private String mCurrList;
    private User mUser;
    private View mDrawerHeader;
    private boolean mDrawerTip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        initSetup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.getInstance().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setAdapterData(List<Shot> newItems) {
        mShotsAdapter.setData(newItems);
    }

    @Override
    protected void appendAdapterData(List<Shot> newItems) {
        mShotsAdapter.appendData(newItems);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startDrawerItemActivity(position);
    }

    private void startDrawerItemActivity(int position) {
        if (mUser == null) {
            Toast.makeText(this, "no login!", Toast.LENGTH_SHORT).show();
            return;
        }

        String item = (String) mDrawerMenuListView.getAdapter()
                .getItem(position);
        switch (item) {
            case "Likes": {
                Bundle args = new Bundle();
                args.putInt("id", mUser.id);
                args.putSerializable("type", ShotListType.ShotsOfLikes);
                args.putInt("count", mUser.likes_count);

                startActivity(ShotListActivity.createIntent(args));
                break;
            }
            case "Following": {
                Bundle args = new Bundle();
                args.putSerializable("type", ShotListType.ShotsOfFollowing);

                startActivity(ShotListActivity.createIntent(args));
                break;
            }
            case "Shots": {

                Bundle args = new Bundle();
                args.putSerializable("type", ShotListType.ShotsOfSelf);
                args.putSerializable("user", mUser);

                startActivity(ShotListActivity.createIntent(args));
                break;
            }
            case "Buckets":
                startActivity(BucketActivity.createIntent(
                        BucketType.BucketsOfSelf, mUser.id, mUser.buckets_count));
                break;
            case "Projects":
                break;
            case "Teams":
                break;
            case "Settings":
                break;
        }

        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onClick(View v) {
        if (v == mDrawerHeader) {
            if (mUser == null) {
                Toast.makeText(this, "mUser is null!", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
            } else {
                startProfileDetailActivity(v);
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent == mSortSpinner) {
            if (mIsSortSpinnerFirst) {
                mIsSortSpinnerFirst = false;
                return;
            }

            mCurrSort = mSortSpinnerArray[position];
            mCurrList = mListSpinnerArray[mListSpinner.getSelectedItemPosition()];
            mCurrTimeFrame =
                    mTimeFrameSpinnerArray[mTimeFrameSpinner.getSelectedItemPosition()];
        } else if (parent == mListSpinner) {
            if (mIsListSpinnerFirst) {
                mIsListSpinnerFirst = false;
                return;
            }
            mCurrSort =
                    mSortSpinnerArray[mSortSpinner.getSelectedItemPosition()];
            mCurrList = mListSpinnerArray[position];
            mCurrTimeFrame =
                    mTimeFrameSpinnerArray[mTimeFrameSpinner.getSelectedItemPosition()];
        } else if (parent == mTimeFrameSpinner) {
            mCurrTimeFrame = mTimeFrameSpinnerArray[position];
            mCurrSort = mSortSpinnerArray[mSortSpinner.getSelectedItemPosition()];
            mCurrList = mListSpinnerArray[mListSpinner.getSelectedItemPosition()];
        }

        if (DribbbleAPIHelper.getInstance().hasAccessToken()) {
            mMainPresenter.firstFetchItems();
            mInitialProgressBar.setVisibility(View.VISIBLE);
            if (mUser == null) {
                mMainPresenter.fetchUserInfo();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public String getCurrTimeFrame() {
        return mCurrTimeFrame;
    }

    @Override
    public String getCurrSort() {
        return mCurrSort;
    }

    @Override
    public String getCurrList() {
        return mCurrList;
    }

    @Override
    public void onFetchUserInfoFinished(User userInfo) {
        mUser = userInfo;

        ((SimpleDraweeView) mDrawerHeader.findViewById(R.id.drawer_user_portrait_image))
                .setImageURI(Uri.parse(userInfo.avatar_url));

        ((TextView) mDrawerHeader.findViewById(R.id.drawer_user_name))
                .setText(userInfo.name);

        ((TextView) mDrawerHeader.findViewById(R.id.drawer_user_username))
                .setText(userInfo.username);

        setHeaderBlurImageURI(Uri.parse(userInfo.avatar_url));

        if (mDrawerTip) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    private void setHeaderBlurImageURI(Uri uri) {
        if (mUser.avatar_url.contains(".gif")) {
            uri = Uri.parse(DribbbleAPI.DEFAULT_HEADER_IMAGE);
        }
        SimpleDraweeView drawerBluerHeaderImage =
                (SimpleDraweeView) mDrawerHeader.findViewById(R.id.drawer_header_blur_image);
        Postprocessor blurPostprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "BlurPostprocessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                // >= level 17
                final RenderScript rs = RenderScript.create(MainActivity.this);
                final Allocation input = Allocation.createFromBitmap(
                        rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                        Allocation.USAGE_SCRIPT);
                final Allocation output = Allocation.createTyped(rs, input.getType());
                final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(
                        rs, Element.U8_4(rs));
                script.setRadius(12);
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
                        .setOldController(drawerBluerHeaderImage.getController())
                                // other setters as you need
                        .build();
        drawerBluerHeaderImage.setController(controller);
    }

    @Override
    public void onFetchUserInfoError() {
        if (mDrawerTip) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
        Toast.makeText(this, "login error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (mUser == null) {
            mMainPresenter.fetchUserInfo();
        }
    }

    @Subscribe
    public void firstFetchShots(TokenInfo tokenInfo) {
        DribbbleAPIHelper.getInstance().setAccessTokenInfo(tokenInfo);
        mMainPresenter.firstFetchItems();
        mDrawerTip = true;
        mMainPresenter.fetchUserInfo();
    }

    @Subscribe
    public void requestToken(AuthorizeInfo info) {
        mMainPresenter.requestToken(info);
    }

    private void initSetup() {
        mMainPresenter.attachView(this);
        setupBase(mMainPresenter);
        setupToolbar();
        setupDrawer();
        setupRecyclerView();
        setupReturnToFab();
        tryToStartLoginActivity();
        EventBusUtil.getInstance().register(this);
    }

    private void setupToolbar() {
        mToolbar.setTitle(getTitle());
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mSortSpinnerArray = getResources().getStringArray(R.array.spinner_sort);
        mListSpinnerArray = getResources().getStringArray(R.array.spinner_list);
        mTimeFrameSpinnerArray = getResources().getStringArray(R.array.spinner_time_frame);
        mCurrTimeFrame = mSortSpinnerArray.length > 0 ? mSortSpinnerArray[0] : "";
        mCurrSort = mListSpinnerArray.length > 0 ? mListSpinnerArray[0] : "";
        mCurrList = mTimeFrameSpinnerArray.length > 0 ? mTimeFrameSpinnerArray[0] : "";

        mSortSpinner.setOnItemSelectedListener(this);
        mListSpinner.setOnItemSelectedListener(this);
        mTimeFrameSpinner.setOnItemSelectedListener(this);
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, 0, 0);
        mDrawerLayout.setDrawerListener(toggle);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.drawer_items));
        mDrawerMenuListView.setAdapter(adapter);
        mDrawerMenuListView.setOnItemClickListener(this);

        mDrawerHeader = LayoutInflater.from(this).inflate(R.layout.drawer_header, null);
        mDrawerHeader.setOnClickListener(this);
        mDrawerMenuListView.addHeaderView(mDrawerHeader);
    }

    private void setupRecyclerView() {
        mShotsAdapter = new ShotsAdapter(null, this);
        mRecyclerView.setAdapter(mShotsAdapter);
    }

    private void setupReturnToFab() {
        mReturnToTopFab.hide(false);
        mReturnToTopFab.setShowAnimation(
                AnimationUtils.loadAnimation(this, R.anim.show_from_bottom));
        mReturnToTopFab.setHideAnimation(
                AnimationUtils.loadAnimation(this, R.anim.hide_to_bottom));

        mReturnToTopFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollOffset() == 0 || dy > 0) {
                    mReturnToTopFab.hide(true);
                } else if (dy < 0) {
                    mReturnToTopFab.show(true);
                }
            }
        });
    }

    private void tryToStartLoginActivity() {
        if (!DribbbleAPIHelper.getInstance().hasAccessToken()) {
            startActivity(AuthorizeActivity.createIntent());
        }
    }


    private void startProfileDetailActivity(View view) {
        final Intent intent = ProfileActivity.createIntent(mUser);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final SimpleDraweeView image = (SimpleDraweeView) view.findViewById(
                R.id.drawer_user_portrait_image);

        if (Utils.hasLollipop()) {
            startActivityLollipop(image, intent, "photo_hero");
        } else {
            startActivityGingerBread(image, intent);
        }
    }
}
