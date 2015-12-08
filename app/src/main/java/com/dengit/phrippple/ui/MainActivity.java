package com.dengit.phrippple.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.ShotsAdapter;
import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.model.AuthorizeInfo;
import com.dengit.phrippple.model.RequestTokenBody;
import com.dengit.phrippple.model.Shot;
import com.dengit.phrippple.model.TokenInfo;
import com.dengit.phrippple.utils.EventBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private DribbbleAPI mDribbbleAPI;

    @Bind(R.id.listview_main)
    ListView mListView;

    @Bind(R.id.main_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private ShotsAdapter mShotsAdapter;
    private String mAccessToken;
    private int mCurrPage;
    private View mFooter;
    private ProgressBar mFooterProgressBar;
    private TextView mLoadMoreTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initSetup();

        Intent intent = getIntent();
        Timber.d(intent.toString());

    }

    private void initSetup() {
        mShotsAdapter = new ShotsAdapter(new ArrayList<Shot>());
        mListView.setAdapter(mShotsAdapter);
        mListView.setOnItemClickListener(this);
        View footerLayout = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
        mFooter = footerLayout.findViewById(R.id.footer);
        mFooterProgressBar = (ProgressBar) footerLayout.findViewById(R.id.footer_progressbar);
        mLoadMoreTV = (TextView) footerLayout.findViewById(R.id.footer_loadmore);

        mFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFooterProgressBar.setVisibility(View.VISIBLE);
                mLoadMoreTV.setText("loading");
                v.setClickable(false);
                ++mCurrPage;
                onListShots(mAccessToken, mCurrPage);

            }
        });
        mListView.addFooterView(footerLayout);

        mRefreshLayout.setOnRefreshListener(this);

        mDribbbleAPI = new Retrofit.Builder()
                .baseUrl(DribbbleAPI.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(DribbbleAPI.class);

        openLoginActivity();

        EventBus.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getInstance().unregister(this);
    }

    private void openLoginActivity() {
        startActivity(AuthorizeActivity.createIntent(getApplicationContext()));
    }


    @Subscribe
    public void listShots(TokenInfo tokenInfo) {

        mCurrPage = 1;
        mAccessToken = tokenInfo.access_token;
        onListShots(mAccessToken, mCurrPage);

    }

    private void onListShots(String accessToken, final int page) {

        final List<Shot> newShots = new ArrayList<>();

        mDribbbleAPI.getShots(accessToken, page)
                .flatMap(new Func1<List<Shot>, Observable<Shot>>() {
                    @Override
                    public Observable<Shot> call(List<Shot> shots) {
                        return Observable.from(shots);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Shot>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("**onCompleted");

                        if (mRefreshLayout.isRefreshing()) {
                            mShotsAdapter.setData(newShots);
                            mRefreshLayout.setRefreshing(false);
                        } else {
                            Timber.d("**mCurrPage: %d", page);
                            mShotsAdapter.appendData(newShots);
                            mFooter.setClickable(true);
                            mFooterProgressBar.setVisibility(View.GONE);
                            mLoadMoreTV.setText("load more");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.d("**onError");
                        e.printStackTrace();
                        if (mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(Shot shot) {
                        Timber.d(shot.title);
                        newShots.add(shot);
                    }
                });
    }

    @Subscribe
    public void passAuthorize(AuthorizeInfo info) {

        mDribbbleAPI.getToken(new RequestTokenBody(info.getCode()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TokenInfo tokenInfo) {
                        Timber.d("**token:%s", tokenInfo.access_token);
                        EventBus.getInstance().post(tokenInfo);
                    }
                });
    }


    @Override
    public void onRefresh() {
        if (TextUtils.isEmpty(mAccessToken)) {
            Timber.d("**login err, token is empty");
            return;
        }

        mRefreshLayout.setRefreshing(true);
        mCurrPage = 1;
        onListShots(mAccessToken, mCurrPage);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ShotActivity.createIntent((Shot) mShotsAdapter.getItem(position)));
    }
}
