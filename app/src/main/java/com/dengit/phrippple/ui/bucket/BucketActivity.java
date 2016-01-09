package com.dengit.phrippple.ui.bucket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.BucketsAdapter;
import com.dengit.phrippple.adapter.RecyclerViewAdapter;
import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.base.FetchBaseActivity;
import com.dengit.phrippple.ui.shotlist.ShotListActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketActivity extends FetchBaseActivity<Bucket> implements BucketView, RecyclerViewAdapter.OnItemClickListener {
    @Inject
    BucketsAdapter mBucketsAdapter;

    @Inject
    BucketPresenter mBucketPresenter;

    private int mId;
    private BucketType mBucketType;

    public static Intent createIntent(BucketType bucketType, int id, int bucketCount) {
        Intent intent = new Intent(APP.getInstance(), BucketActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", bucketType);
        intent.putExtra("bucketCount", bucketCount);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        initSetup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBucketPresenter.detachView();
    }

    @Override
    protected void appendAdapterData(List<Bucket> newItems) {
        mBucketsAdapter.appendData(newItems);
    }

    @Override
    protected void setAdapterData(List<Bucket> newItems) {
        mBucketsAdapter.setData(newItems);
    }

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public BucketType getBucketType() {
        return mBucketType;
    }

    @Override
    public void onItemClick(View view, int position) {
        Bucket bucket = (Bucket) mBucketsAdapter.getItem(position);
        Bundle args = new Bundle();
        args.putInt("id", bucket.id);
        args.putSerializable("type", ShotListType.ShotsOfBucket);
        args.putInt("count", bucket.shots_count);

        startActivity(ShotListActivity.createIntent(args));
    }

    private void initSetup() {
        mBucketPresenter.attachView(this);
        setupBase(mBucketPresenter);

        mBucketType = (BucketType) getIntent().getSerializableExtra("type");
        mId = getIntent().getIntExtra("id", 0);
        int bucketCount = getIntent().getIntExtra("bucketCount", 0);
        setTitle(bucketCount + " buckets");

        mBucketsAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mBucketsAdapter);

        mBucketPresenter.firstFetchItems();
    }

}
