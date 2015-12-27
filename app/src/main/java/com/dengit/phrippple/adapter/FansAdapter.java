package com.dengit.phrippple.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Fan;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class FansAdapter extends BaseAdapter {

    private List<Fan> mFans;

    public FansAdapter(List<Fan> fans) {
        mFans = fans;
    }

    @Override
    public int getCount() {
        return mFans.size();
    }

    @Override
    public Object getItem(int position) {
        return mFans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(APP.getInstance()).inflate(R.layout.item_fan, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setUpFanItem(holder, position);
        return convertView;
    }

    public void setData(List<Fan> newShots) {
        mFans.clear();
        appendData(newShots);
    }

    public void appendData(List<Fan> newShots) {
        mFans.addAll(newShots);
        notifyDataSetChanged();
    }

    private void setUpFanItem(ViewHolder holder, int position) {
        Fan fan = (Fan) getItem(position);
        holder.fanPortrait.setImageURI(Uri.parse(fan.user.avatar_url));
        holder.fanName.setText(fan.user.name);
        holder.fanPrettyTime.setText(fan.created_at);

        if (fan.user.shots_count > 0) {
            holder.fanShotCount.setText(fan.user.shots_count + " shots");
            holder.fanShotCount.setVisibility(View.VISIBLE);
        }

        if (fan.user.followers_count > 0) {
            holder.fanFollowerCount.setText(fan.user.followers_count + " followers");
            holder.fanFollowerCount.setVisibility(View.VISIBLE);
        }

        if (fan.user.shots_count > 0 && fan.user.followers_count > 0) {
            holder.fanItemDivider.setVisibility(View.VISIBLE);
        }

        if (fan.user.location != null && !TextUtils.isEmpty(fan.user.location.trim())) {
            holder.fanLocation.setVisibility(View.VISIBLE);
            holder.fanLocation.setText(fan.user.location);
        }
    }

    static class ViewHolder {
        @Bind(R.id.fan_portrait_image)
        SimpleDraweeView fanPortrait;

        @Bind(R.id.fan_name)
        TextView fanName;

        @Bind(R.id.fan_pretty_time)
        TextView fanPrettyTime;

        @Bind(R.id.fan_shot_count)
        TextView fanShotCount;

        @Bind(R.id.fan_follower_count)
        TextView fanFollowerCount;

        @Bind(R.id.fan_location)
        TextView fanLocation;

        @Bind(R.id.fan_item_divider)
        View fanItemDivider;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
