package com.dengit.phrippple.ui.bucket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.BucketsAdapter;
import com.dengit.phrippple.api.DribbbleAPI;
import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.injection.component.DaggerActivityComponent;
import com.dengit.phrippple.ui.base.FetchBaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketActivity extends FetchBaseActivity<Bucket> implements BucketView {
    @Inject
    BucketPresenter mBucketPresenter;

    private int mId;
    private BucketType mBucketType;
    private BucketsAdapter mBucketsAdapter;

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
        ButterKnife.bind(this);

        initSetup();
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

    private void initSetup() {
        setupComponent();
        mBucketPresenter.attachView(this);
        setupBase(mBucketPresenter);

        mBucketType = (BucketType) getIntent().getSerializableExtra("type");
        mId = getIntent().getIntExtra("id", 0);
        int bucketCount = getIntent().getIntExtra("bucketCount", 0);
        setTitle(bucketCount + " buckets");

        mBucketsAdapter = new BucketsAdapter(this);
        mRecyclerView.setAdapter(mBucketsAdapter);

        mBucketPresenter.firstFetchItems();
    }

    private void setupComponent() {
        DaggerActivityComponent.builder()
                .aPPComponent(APP.getInstance().getComponent())
                .build()
                .inject(this);
    }
}
