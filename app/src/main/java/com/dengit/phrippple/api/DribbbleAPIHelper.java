package com.dengit.phrippple.api;

import com.dengit.phrippple.data.TokenInfo;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class DribbbleAPIHelper {
    private static DribbbleAPIHelper mHelper;

    private DribbbleAPI mDribbbleAPI;
    private TokenInfo mTokenInfo;

    private DribbbleAPIHelper() {
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
        mTokenInfo = new TokenInfo();
        mTokenInfo.access_token = tokenInfo.access_token;
        mTokenInfo.scope = tokenInfo.scope;
        mTokenInfo.token_type = tokenInfo.token_type;
    }

    public String getAccessToken() {
        if (mTokenInfo == null) {
            Timber.d("**mTokenInfo == null");
            return "";
        }

        return mTokenInfo.access_token;
    }
}
