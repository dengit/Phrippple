package com.dengit.phrippple.ui.bucket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.BucketsAdapter;
import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

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
    private int mUserId;
    private BucketPresenter mBucketPresenter;

    public static Intent createIntent(int userId, int bucketCount) {
        Intent intent = new Intent(APP.getInstance(), BucketActivity.class);
        intent.putExtra("userId", userId);
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
        mUserId = getIntent().getIntExtra("userId", 0);
        int bucketCount = getIntent().getIntExtra("bucketCount", 0);
        setTitle(bucketCount + " buckets");
        mBucketsAdapter = new BucketsAdapter(new ArrayList<Bucket>());
        mBucketList.setAdapter(mBucketsAdapter);

        EventBusUtil.getInstance().register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBucketPresenter.onResume(mUserId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.getInstance().unregister(this);
    }


    @Subscribe
    public void appendData(ArrayList<Bucket> buckets) {
        mBucketsAdapter.appendData(buckets);
    }
}
