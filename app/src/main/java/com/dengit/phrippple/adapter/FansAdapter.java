package com.dengit.phrippple.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Fan;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class FansAdapter extends RecyclerViewAdapter<Fan> {

    @Inject
    public FansAdapter() {
        super(new ArrayList<Fan>());
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.item_fan;
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolderItem(View itemView) {
        return new FanVHItem(itemView);
    }

    protected void setupItems(VHItemBase holder, final int position) {
        FanVHItem itemHolder = (FanVHItem) holder;
        Fan fan = (Fan) getItem(position);
        itemHolder.fanPortrait.setImageURI(Uri.parse(fan.user.avatar_url));
        itemHolder.fanName.setText(fan.user.name);
        itemHolder.fanPrettyTime.setText(fan.created_at);

        if (fan.user.shots_count > 0) {
            itemHolder.fanShotCount.setText(fan.user.shots_count + " shots");
            itemHolder.fanShotCount.setVisibility(View.VISIBLE);
        }

        if (fan.user.followers_count > 0) {
            itemHolder.fanFollowerCount.setText(fan.user.followers_count + " followers");
            itemHolder.fanFollowerCount.setVisibility(View.VISIBLE);
        }

        if (fan.user.shots_count > 0 && fan.user.followers_count > 0) {
            itemHolder.fanItemDivider.setVisibility(View.VISIBLE);
        }

        if (fan.user.location != null && !TextUtils.isEmpty(fan.user.location.trim())) {
            itemHolder.fanLocation.setVisibility(View.VISIBLE);
            itemHolder.fanLocation.setText(fan.user.location);
        }

    }

    class FanVHItem extends VHItemBase implements View.OnClickListener {
        View fanItemView;

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

        public FanVHItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
            fanItemView = view;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
