package com.dengit.phrippple.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Shot;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotsAdapter extends BaseAdapter{

    private List<Shot> mShots;

    public ShotsAdapter(List<Shot> mShots) {
        this.mShots = mShots;
    }


    public void addItem(Shot shot) {
        mShots.add(shot);
    }


    @Override
    public int getCount() {
        return mShots.size();
    }

    @Override
    public Object getItem(int position) {
        return mShots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(APP.getInstance()).inflate(R.layout.shot_item, parent, false);

            holder = new ViewHolder();
            holder.tasterImage = (SimpleDraweeView) convertView.findViewById(R.id.shot_item_taster_image);
            holder.authorImage = (SimpleDraweeView) convertView.findViewById(R.id.shot_item_author_image);
            holder.likeTV = (TextView) convertView.findViewById(R.id.shot_item_like);
            holder.msgTV = (TextView) convertView.findViewById(R.id.shot_item_msg);
            holder.viewTV = (TextView) convertView.findViewById(R.id.shot_item_view);
            holder.authorNameTV = (TextView) convertView.findViewById(R.id.shot_item_author_name);
            holder.titleTV = (TextView) convertView.findViewById(R.id.shot_item_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setUpShotItem(holder, position);

        return convertView;
    }

    private void setUpShotItem(ViewHolder holder, int position) {
        Shot shot = (Shot) getItem(position);
        holder.tasterImage.setImageURI(Uri.parse(shot.images.teaser));
        holder.authorImage.setImageURI(Uri.parse(shot.user.avatar_url));
        holder.likeTV.setText(String.valueOf(shot.views_count));
        holder.msgTV.setText(String.valueOf(shot.comments_count));
        holder.viewTV.setText(String.valueOf(shot.likes_count));
        holder.authorNameTV.setText(String.valueOf(shot.user.name));
        holder.titleTV.setText(String.valueOf(shot.title));
    }

    public void setData(List<Shot> newShots) {
        mShots.clear();
        appendData(newShots);
    }

    public void appendData(List<Shot> newShots) {
        mShots.addAll(newShots);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public SimpleDraweeView tasterImage;
        public SimpleDraweeView authorImage;
        public TextView likeTV;
        public TextView msgTV;
        public TextView viewTV;
        public TextView authorNameTV;
        public TextView titleTV;
    }
}
