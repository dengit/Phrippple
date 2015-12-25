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
import com.facebook.drawee.view.SimpleDraweeView;

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
        return mBuckets.get(position).id;
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

    public void setData(List<Bucket> newShots) {
        mBuckets.clear();
        appendData(newShots);
    }

    public void appendData(List<Bucket> newShots) {
        mBuckets.addAll(newShots);
        notifyDataSetChanged();
    }

    private void setUpBucketItem(ViewHolder holder, int position) {
        Bucket bucket = (Bucket) getItem(position);
        holder.bucketName.setText(bucket.name);

        if (bucket.user != null) {
            holder.bucketOwnerPortrait.setImageURI(Uri.parse(bucket.user.avatar_url));
            holder.bucketOwnerName.setText(bucket.user.name);
            holder.bucketOwnerPortrait.setVisibility(View.VISIBLE);
            holder.bucketOwnerName.setVisibility(View.VISIBLE);
        }

        holder.bucketShotCount.setText(bucket.shots_count + " shots");
    }


    static class ViewHolder {
        @Bind(R.id.bucket_name)
        TextView bucketName;
        @Bind(R.id.bucket_owner_portrait)
        SimpleDraweeView bucketOwnerPortrait;
        @Bind(R.id.bucket_owner_name)
        TextView bucketOwnerName;
        @Bind(R.id.bucket_shot_count)
        TextView bucketShotCount;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
