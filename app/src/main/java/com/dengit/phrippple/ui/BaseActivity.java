package com.dengit.phrippple.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dengit on 15/12/7.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

}
