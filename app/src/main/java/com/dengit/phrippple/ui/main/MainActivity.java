package com.dengit.phrippple.ui.main;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.api.DribbbleAPIHelper;
import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.TokenInfo;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.TransitionBaseActivity;
import com.dengit.phrippple.ui.login.AuthorizeActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.dengit.phrippple.utils.Utils;
import com.dengit.phrippple.widget.ResideMenu;
import com.dengit.phrippple.widget.ResideMenuItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends TransitionBaseActivity<Shot> implements MainView<Shot>, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @Bind(R.id.fab_return_to_top)
    FloatingActionButton mReturnToTopFab;

    @Inject
    MainPresenter<Shot> mMainPresenter;

    private ShotsAdapter mShotsAdapter;
    private int mOldFirstVisibleItem;
    private ResideMenu mResideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;

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

    @Subscribe
    public void firstFetchShots(TokenInfo tokenInfo) {
        DribbbleAPIHelper.getInstance().setAccessTokenInfo(tokenInfo);
        mMainPresenter.firstFetchItems();
    }

    @Subscribe
    public void requestToken(AuthorizeInfo info) {
        mMainPresenter.requestToken(info);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startDetailActivity(view, position);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == itemHome) {
            //            changeFragment(new HomeFragment());
            Toast.makeText(this, "itemHome", Toast.LENGTH_SHORT).show();
            mResideMenu.closeMenu();
        } else if (v == itemProfile) {
            //            changeFragment(new ProfileFragment());
            Toast.makeText(this, "itemProfile", Toast.LENGTH_SHORT).show();
            mResideMenu.closeMenu();
        } else if (v == itemCalendar) {
            //            changeFragment(new CalendarFragment());
            Toast.makeText(this, "itemCalendar", Toast.LENGTH_SHORT).show();
            mResideMenu.closeMenu();
        } else if (v == itemSettings) {
            //            changeFragment(new SettingsFragment());
            Toast.makeText(this, "itemSettings", Toast.LENGTH_SHORT).show();
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

    private void initSetup() {
        setupToolbar();
        setupComponent();
        setBasePresenter(mMainPresenter);
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>(), this);
        mListView.setAdapter(mShotsAdapter);
        mListView.setOnItemClickListener(this);
        setupReturnToFab(mListView);
        setupResideMenu();
        initBase();

        tryToStartLoginActivity();
        EventBusUtil.getInstance().register(this);
    }

    private void setupToolbar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
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
        itemProfile = new ResideMenuItem(this, R.drawable.ic_menu_profile, "Profile");
        itemCalendar = new ResideMenuItem(this, R.drawable.ic_menu_calendar, "Calendar");
        itemSettings = new ResideMenuItem(this, R.drawable.ic_menu_settings, "Settings");

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        mResideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
        mResideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
    }

    private void tryToStartLoginActivity() {
        if (!DribbbleAPIHelper.getInstance().hasAccessToken()) {
            startActivity(AuthorizeActivity.createIntent());
        } else {
            mMainPresenter.firstFetchItems();
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
        final SimpleDraweeView shotImage = (SimpleDraweeView) view.findViewById(R.id.shot_item_image);

        intent.putExtra("shot", shot);
        if (Utils.hasLollipop()) {
            startActivityLollipop(shotImage, intent, "photo_hero");
        } else {
            startActivityGingerBread(shotImage, intent);
        }
    }

}
