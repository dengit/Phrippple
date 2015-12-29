package com.dengit.phrippple.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.data.TokenInfo;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.TransitionBaseActivity;
import com.dengit.phrippple.ui.bucket.BucketActivity;
import com.dengit.phrippple.ui.login.AuthorizeActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;
import com.dengit.phrippple.ui.shotlist.ShotListActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.dengit.phrippple.utils.Utils;
import com.dengit.phrippple.widget.ResideMenu;
import com.dengit.phrippple.widget.ResideMenuItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends TransitionBaseActivity<Shot> implements MainView<Shot>, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, AdapterView.OnItemSelectedListener {

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
    MainPresenter<Shot> mMainPresenter;

    private ShotsAdapter mShotsAdapter;
    private int mOldFirstVisibleItem;
    private ResideMenu mResideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemFollowing;
    private ResideMenuItem itemLikes;
    private ResideMenuItem itemShots;
    private ResideMenuItem itemBuckets;
    private ResideMenuItem itemProjects;
    private ResideMenuItem itemTeams;
    private ResideMenuItem itemSettings;
    private boolean mIsSortSpinnerFirst = true;
    private boolean mIsListSpinnerFirst = true;
    private String[] mSortSpinnerArray;
    private String[] mListSpinnerArray;
    private String[] mTimeFrameSpinnerArray;
    private String mCurrTimeFrame;
    private String mCurrSort;
    private String mCurrList;
    private User mUser;
    private View mDrawerMenuHeader;
    private boolean mDrawerTip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initSetup();

        Intent intent = getIntent();
        Timber.d(intent.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.getInstance().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mResideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
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
        if (parent == mListView) {
            startShotDetailActivity(view, position);
        } else if (parent == mDrawerMenuListView) {
            startDrawerItemActivity(position);
        }
    }

    private void startDrawerItemActivity(int position) {
        if (mUser == null) {
            Toast.makeText(this, "no login!", Toast.LENGTH_SHORT).show();
            return;
        }

        String item = (String) mDrawerMenuListView.getAdapter().getItem(position);
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
                args.putInt("id", mUser.id);
                args.putSerializable("type", ShotListType.ShotsOfLikes);
                args.putInt("count", mUser.likes_count);

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
                startActivity(BucketActivity.createIntent(BucketType.BucketsOfSelf, mUser.id, mUser.buckets_count));
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
        super.onClick(v);
        if (v == mDrawerMenuHeader) {
            if (mUser == null) {
                Toast.makeText(this, "login first!", Toast.LENGTH_SHORT).show();
            } else {
                startProfileDetailActivity(v);
            }
            mDrawerLayout.closeDrawers();
        } else if (v == itemHome) {
            //            changeFragment(new HomeFragment());
            Toast.makeText(this, "itemHome", Toast.LENGTH_SHORT).show();
        } else if (v == itemFollowing) {
            //            changeFragment(new ProfileFragment());
            Toast.makeText(this, "itemProfile", Toast.LENGTH_SHORT).show();
        } else if (v == itemLikes) {
            //            changeFragment(new CalendarFragment());
            Toast.makeText(this, "itemCalendar", Toast.LENGTH_SHORT).show();
        } else if (v == itemShots) {
            //            changeFragment(new ProfileFragment());
            Toast.makeText(this, "itemProfile", Toast.LENGTH_SHORT).show();
        } else if (v == itemBuckets) {
            //            changeFragment(new CalendarFragment());
            Toast.makeText(this, "itemCalendar", Toast.LENGTH_SHORT).show();
        } else if (v == itemProjects) {
            //            changeFragment(new ProfileFragment());
            Toast.makeText(this, "itemProfile", Toast.LENGTH_SHORT).show();
        } else if (v == itemTeams) {
            //            changeFragment(new CalendarFragment());
            Toast.makeText(this, "itemCalendar", Toast.LENGTH_SHORT).show();
        } else if (v == itemSettings) {
            //            changeFragment(new SettingsFragment());
            Toast.makeText(this, "itemSettings", Toast.LENGTH_SHORT).show();

        }

        if (v instanceof ResideMenuItem) {
            mResideMenu.closeMenu();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem > mOldFirstVisibleItem) {
            mReturnToTopFab.hide(true);
        } else if (firstVisibleItem < mOldFirstVisibleItem) {
            mReturnToTopFab.show(true);
        }
        mOldFirstVisibleItem = firstVisibleItem;
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
            mCurrTimeFrame = mTimeFrameSpinnerArray[mTimeFrameSpinner.getSelectedItemPosition()];
        } else if (parent == mListSpinner) {
            if (mIsListSpinnerFirst) {
                mIsListSpinnerFirst = false;
                return;
            }
            mCurrSort = mSortSpinnerArray[mSortSpinner.getSelectedItemPosition()];
            mCurrList = mListSpinnerArray[position];
            mCurrTimeFrame = mTimeFrameSpinnerArray[mTimeFrameSpinner.getSelectedItemPosition()];
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
        ((SimpleDraweeView) mDrawerMenuHeader.findViewById(R.id.drawer_user_portrait_image)).setImageURI(Uri.parse(userInfo.avatar_url));
        ((TextView)mDrawerMenuHeader.findViewById(R.id.drawer_user_name)).setText(userInfo.name);
        ((TextView) mDrawerMenuHeader.findViewById(R.id.drawer_user_username)).setText(userInfo.username);

        if (mDrawerTip) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
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
        setupToolbar();
        setupDrawer();
        setupComponent();
        setBasePresenter(mMainPresenter);
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>(), this);
        mListView.setAdapter(mShotsAdapter);
        mListView.setOnItemClickListener(this);
        setupReturnToFab(mListView);
//        setupResideMenu();
        initBase();

        tryToStartLoginActivity();
        EventBusUtil.getInstance().register(this);
    }

    private void setupToolbar() {
        mToolbar.setTitle(getTitle());
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            //            actionBar.setDisplayHomeAsUpEnabled(true);
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);
        mDrawerLayout.setDrawerListener(toggle);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.drawer_items));
        mDrawerMenuListView.setAdapter(adapter);
        mDrawerMenuListView.setOnItemClickListener(this);

        mDrawerMenuHeader = LayoutInflater.from(this).inflate(R.layout.drawer_menu_header, null);
        mDrawerMenuHeader.setOnClickListener(this);
        mDrawerMenuListView.addHeaderView(mDrawerMenuHeader);
    }

    private void setupComponent() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    private void setupReturnToFab(final ListView listView) {
        mReturnToTopFab.hide(false);
        mReturnToTopFab.setShowAnimation(AnimationUtils.loadAnimation(this, R.anim.show_from_bottom));
        mReturnToTopFab.setHideAnimation(AnimationUtils.loadAnimation(this, R.anim.hide_to_bottom));

        mReturnToTopFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.smoothScrollToPosition(0);
            }
        });

        listView.setOnScrollListener(this);
    }

    private void setupResideMenu() {
        mResideMenu = new ResideMenu(this);
        mResideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        mResideMenu.setBackground(R.drawable.menu_reside_bg);
        mResideMenu.attachToActivity(this);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.ic_menu_home, "Home");
        itemFollowing = new ResideMenuItem(this, R.drawable.ic_menu_calendar, "Following");
        itemLikes = new ResideMenuItem(this, R.drawable.ic_menu_calendar, "Likes");
        itemShots = new ResideMenuItem(this, R.drawable.ic_menu_calendar, "Shots");
        itemBuckets = new ResideMenuItem(this, R.drawable.ic_menu_calendar, "Buckets");
        itemProjects = new ResideMenuItem(this, R.drawable.ic_menu_calendar, "Projects");
        itemTeams = new ResideMenuItem(this, R.drawable.ic_menu_calendar, "Teams");
        itemSettings = new ResideMenuItem(this, R.drawable.ic_menu_settings, "Settings");

        itemHome.setOnClickListener(this);
        itemFollowing.setOnClickListener(this);
        itemLikes.setOnClickListener(this);
        itemShots.setOnClickListener(this);
        itemBuckets.setOnClickListener(this);
        itemProjects.setOnClickListener(this);
        itemTeams.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        mResideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemFollowing, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemLikes, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemShots, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemBuckets, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemProjects, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemTeams, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
    }

    private void tryToStartLoginActivity() {
        if (!DribbbleAPIHelper.getInstance().hasAccessToken()) {
            startActivity(AuthorizeActivity.createIntent());
        }
    }

    /**
     * When the user clicks a thumbnail, bundle up information about it and launch the
     * details activity.
     */
    private void startShotDetailActivity(View view, int position) {
        final Intent intent = new Intent();
        intent.setClass(this, ShotActivity.class);

        Shot shot = (Shot) mShotsAdapter.getItem(position);
        final SimpleDraweeView shotImage = (SimpleDraweeView) view.findViewById(R.id.shot_item_image);

        intent.putExtra("shot", shot);
        if (Utils.hasLollipop()) {
            startActivityLollipop(shotImage, intent, "photo_hero");
        } else {
            startActivityGingerBread(shotImage, intent);
        }
    }

    private void startProfileDetailActivity(View view) {
        final Intent intent = ProfileActivity.createIntent(mUser);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.drawer_user_portrait_image);

        if (Utils.hasLollipop()) {
            startActivityLollipop(image, intent, "photo_hero");
        } else {
            startActivityGingerBread(image, intent);
        }
    }
}
