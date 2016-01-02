package com.dengit.phrippple.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.data.TokenInfo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class DribbbleAPIHelper {
    private static DribbbleAPIHelper mHelper;

    private TokenInfo mTokenInfo;
    private DribbbleAPI mDribbbleAPI;

    private DribbbleAPIHelper() {
        mTokenInfo = new TokenInfo();
        mDribbbleAPI = new Retrofit.Builder()
                .baseUrl(DribbbleAPI.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(DribbbleAPI.class);
    }

    public synchronized static DribbbleAPIHelper getInstance() {
        if (mHelper == null) {
            mHelper = new DribbbleAPIHelper();
        }

        return mHelper;
    }

    public DribbbleAPI getDribbbleAPI() {
        return mDribbbleAPI;
    }

    public void setAccessTokenInfo(TokenInfo tokenInfo) {
        Timber.d("**setAccessTokenInfo");
        if (TextUtils.isEmpty(tokenInfo.access_token)) {
            Timber.d("**tokenInfo.access_token is Empty");
            return;
        }

        mTokenInfo.access_token = tokenInfo.access_token;
        mTokenInfo.scope = tokenInfo.scope;
        mTokenInfo.token_type = tokenInfo.token_type;

        save(tokenInfo);
    }

    public boolean hasAccessToken() {
        return !TextUtils.isEmpty(getAccessToken());
    }

    public String getAccessToken() {
        Timber.d("**getAccessToken");
        if (TextUtils.isEmpty(mTokenInfo.access_token)) {
            load(mTokenInfo);
        }

        if (TextUtils.isEmpty(mTokenInfo.access_token)) {
            Timber.d("**mTokenInfo.access_token is Empty");
            return "";
        }

        return mTokenInfo.access_token;
    }

    private void save(TokenInfo tokenInfo) {
        SharedPreferences prefs = APP.getInstance().getSharedPreferences(
                APP.getInstance().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", tokenInfo.access_token);
        editor.putString("scope", tokenInfo.scope);
        editor.putString("token_type", tokenInfo.token_type);
        editor.apply();
    }

    private void load(TokenInfo tokenInfo) {
        SharedPreferences prefs = APP.getInstance().getSharedPreferences(
                APP.getInstance().getPackageName(), Context.MODE_PRIVATE);
        if (!prefs.contains("access_token")) {
            return;
        }

        tokenInfo.access_token = prefs.getString("access_token", "");
        tokenInfo.scope = prefs.getString("scope", "");
        tokenInfo.token_type = prefs.getString("token_type", "");
    }

    public static byte[] getColorsAco(int shotId) {
        try {
            String url = String.format("https://dribbble.com/shots/%d/colors.aco", shotId);
            Timber.d("**ColorsAco url: %s", url);

            return new OkHttpClient().newCall(new Request.Builder().url(url).build()).execute().body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
