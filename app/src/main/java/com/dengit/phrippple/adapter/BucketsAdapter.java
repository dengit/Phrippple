package com.dengit.phrippple.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Bucket;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class BucketsAdapter extends BaseAdapter {

    private List<Bucket> mBuckets;

    public BucketsAdapter(List<Bucket> buckets) {
        mBuckets = buckets;
    }

    @Override
    public int getCount() {
        return mBuckets.size();
    }

    @Override
    public Object getItem(int position) {
        return mBuckets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(APP.getInstance()).inflate(R.layout.item_bucket, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setUpBucketItem(holder, position);

        return convertView;
    }

    private void setUpBucketItem(ViewHolder holder, int position) {
        Bucket bucket = (Bucket) getItem(position);
        holder.bucketName.setText(bucket.name);
        holder.shotCount.setText(bucket.shots_count + " shots");
        holder.createTime.setText(bucket.created_at);
    }

    public void setData(List<Bucket> newShots) {
        mBuckets.clear();
        appendData(newShots);
    }

    public void appendData(List<Bucket> newShots) {
        mBuckets.addAll(newShots);
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @Bind(R.id.bucket_name)
        TextView bucketName;
        @Bind(R.id.shot_count)
        TextView shotCount;
        @Bind(R.id.create_time)
        TextView createTime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
