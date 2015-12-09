package com.dengit.phrippple.ui.shot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.model.Shot;
import com.dengit.phrippple.ui.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotActivity extends BaseActivity {
    @Bind(R.id.shot_normal_image)
    SimpleDraweeView mShotNormalImage;
    private Shot mShot;

    public static Intent createIntent(Shot shot) {
        Intent intent = new Intent(APP.getInstance(), ShotActivity.class);
        intent.putExtra("shot", shot);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);
        ButterKnife.bind(this);

        initSetup();
    }

    private void initSetup() {
        mShot = (Shot) getIntent().getSerializableExtra("shot");

        Timber.d("**mShot.images.normal:%s", mShot.images.normal);

        mShotNormalImage.setImageURI(Uri.parse(mShot.images.normal));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
