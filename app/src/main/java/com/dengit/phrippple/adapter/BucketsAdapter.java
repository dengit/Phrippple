package com.dengit.phrippple.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.utils.Util;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

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
            convertView = LayoutInflater.from(APP.getInstance()).inflate(R.layout.bucket_item, parent, false);

            holder = new ViewHolder();
            holder.bucketName = (TextView) convertView.findViewById(R.id.bucket_name);
            holder.shotCount = (TextView) convertView.findViewById(R.id.shot_count);
            holder.createTime = (TextView) convertView.findViewById(R.id.create_time);

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


    private static class ViewHolder {
        public TextView bucketName;
        public TextView shotCount;
        public TextView createTime;
    }
}
