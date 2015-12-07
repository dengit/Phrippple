package com.dengit.phrippple.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dengit.phrippple.R;
import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.model.AuthorizeInfo;
import com.dengit.phrippple.utils.EventBus;

import timber.log.Timber;

/**
 * Created by dengit on 15/12/7.
 */
public class AuthorizeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        WebView loginWebview = (WebView) findViewById(R.id.webview_login);

        loginWebview.clearCache(true);

        String url = String.format(DribbbleAPI.AUTHORIZE_URL, DribbbleAPI.CLIENT_ID);

        loginWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Timber.d("**url:%s", url);

                Uri uri = Uri.parse(url);

                if (uri.getScheme().equals("phrippple")
                        && uri.getHost().equals("phone-callback")) {

                    String code = uri.getQueryParameter("code");

                    //todo use scheme


                    EventBus.getInstance().post(new AuthorizeInfo(code));
                    finish();
                    return true;
                }

                return false;
            }
        });
        loginWebview.loadUrl(url);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, AuthorizeActivity.class);
    }
}
