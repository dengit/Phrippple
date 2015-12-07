package com.dengit.phrippple;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.model.AuthorizeInfo;
import com.dengit.phrippple.model.RequestTokenBody;
import com.dengit.phrippple.model.Shot;
import com.dengit.phrippple.model.TokenInfo;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.AuthorizeActivity;
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

public class MainActivity extends BaseActivity {

    private DribbbleAPI mDribbbleAPI;

    @Bind(R.id.listview_main)
    ListView mListView;

    private ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        Timber.d(intent.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DribbbleAPI.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mDribbbleAPI = retrofit.create(DribbbleAPI.class);

        openLoginActivity();

        EventBus.getInstance().register(this);

        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());

        mListView.setAdapter(mArrayAdapter);

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

        String accessToken = tokenInfo.access_token;
        mDribbbleAPI.getShots(accessToken)
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
                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.d("**onError");
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(Shot shot) {
                        Timber.d(shot.title);
                        mArrayAdapter.add(shot.title);
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


}
