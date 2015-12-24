package com.dengit.phrippple.ui.bucket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.BucketsAdapter;
import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.data.BucketType;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.ShotListType;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;
import com.dengit.phrippple.ui.shotlist.ShotListActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketActivity extends BaseActivity<Bucket> implements BucketView<Bucket>, AdapterView.OnItemClickListener {

    private int mId;
    private BucketType mBucketType;
    private BucketsAdapter mBucketsAdapter;
    private BucketPresenter<Bucket> mBucketPresenter;

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bucket bucket = (Bucket) mBucketsAdapter.getItem(position);
        Bundle args = new Bundle();
        args.putInt("id", bucket.id);
        args.putSerializable("type", ShotListType.ShotsOfBucket);
        args.putInt("count", bucket.shots_count);

        startActivity(ShotListActivity.createIntent(args));
    }

    private void initSetup() {
        mBucketPresenter = new BucketPresenterImpl<>(this);
        setBasePresenter(mBucketPresenter);
        mBucketType = (BucketType) getIntent().getSerializableExtra("type");
        mId = getIntent().getIntExtra("id", 0);
        int bucketCount = getIntent().getIntExtra("bucketCount", 0);
        setTitle(bucketCount + " buckets");
        mBucketsAdapter = new BucketsAdapter(new ArrayList<Bucket>());
        mListView.setAdapter(mBucketsAdapter);
        mListView.setOnItemClickListener(this);

        initBase();
        mBucketPresenter.firstFetchItems();
    }

}
