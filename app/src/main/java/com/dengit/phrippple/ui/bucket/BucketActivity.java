package com.dengit.phrippple.ui.bucket;

import android.content.Intent;
import android.os.Bundle;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.ui.BaseActivity;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static Intent createIntent(int userId) {
        Intent intent = new Intent(APP.getInstance(), BucketActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }
}
