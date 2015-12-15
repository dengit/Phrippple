package com.dengit.phrippple.ui.bucket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.BucketsAdapter;
import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketActivity extends BaseActivity implements BucketView {

    @Bind(R.id.bucket_list_view)
    ListView mBucketList;

    private BucketsAdapter mBucketsAdapter;
    private int mId;
    private BucketType mBucketType;
    private BucketPresenter mBucketPresenter;

    public static Intent createIntent(BucketType bucketType, int id, int bucketCount) {
        Intent intent = new Intent(APP.getInstance(), BucketActivity.class);
        intent.putExtra("type", bucketType);
        intent.putExtra("id", id);
        intent.putExtra("bucketCount", bucketCount);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);
        ButterKnife.bind(this);

        initSetup();
    }

    private void initSetup() {
        mBucketPresenter = new BucketPresenterImpl(this);
        mBucketType = (BucketType) getIntent().getSerializableExtra("type");
        mId = getIntent().getIntExtra("id", 0);
        int bucketCount = getIntent().getIntExtra("bucketCount", 0);
        setTitle(bucketCount + " buckets");
        mBucketsAdapter = new BucketsAdapter(new ArrayList<Bucket>());
        mBucketList.setAdapter(mBucketsAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusUtil.getInstance().register(this);
        mBucketPresenter.onResume(mBucketType, mId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusUtil.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Subscribe
    public void appendData(ArrayList<Bucket> buckets) {
        mBucketsAdapter.appendData(buckets);
    }
}
