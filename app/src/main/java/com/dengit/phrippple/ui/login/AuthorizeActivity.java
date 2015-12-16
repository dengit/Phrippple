package com.dengit.phrippple.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.AuthorizeInfo;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.SuperBaseActivity;
import com.dengit.phrippple.utils.EventBusUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/7.
 */
public class AuthorizeActivity extends SuperBaseActivity {

    @Bind(R.id.webview_login)
    WebView mLoginWebview;

    public static Intent createIntent() {
        return new Intent(APP.getInstance(), AuthorizeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initSetup();
    }

    private void initSetup() {
        mLoginWebview.setWebViewClient(new AuthWebViewClient());
        mLoginWebview.loadUrl(DribbbleAPI.AUTHORIZE_URL);
    }


    private class AuthWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Timber.d("**url:%s", url);

            Uri uri = Uri.parse(url);

            if (!isPhoneCallback(uri)) {
                return false;
            }

            String code = uri.getQueryParameter("code");
            //todo use scheme
            EventBusUtil.getInstance().post(new AuthorizeInfo(code));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });

            return true;
        }

        private boolean isPhoneCallback(Uri uri) {
            return uri.getScheme().equals(DribbbleAPI.CALLBACK_SCHEME)
                    && uri.getHost().equals(DribbbleAPI.CALLBACK_HOST);
        }
    }
}
