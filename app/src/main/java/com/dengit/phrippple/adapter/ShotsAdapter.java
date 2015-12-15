package com.dengit.phrippple.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotsAdapter extends BaseAdapter {

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
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setUpShotItem(holder, position);

        return convertView;
    }

    private void setUpShotItem(ViewHolder holder, int position) {
        final Shot shot = (Shot) getItem(position);
        holder.shotImage.setImageURI(Uri.parse(shot.images.normal));
        holder.authorImage.setImageURI(Uri.parse(shot.user.avatar_url));
        holder.likeTV.setText(String.valueOf(shot.views_count));
        holder.msgTV.setText(String.valueOf(shot.comments_count));
        holder.viewTV.setText(String.valueOf(shot.likes_count));
        holder.authorNameTV.setText(String.valueOf(shot.user.name));
        holder.titleTV.setText(String.valueOf(shot.title));

        if (shot.animated) {
            holder.gifTag.setVisibility(View.VISIBLE);
        } else {
            holder.gifTag.setVisibility(View.GONE);
        }


        //todo new listener every time
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ProfileActivity.createIntent(shot.user);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                APP.getInstance().startActivity(intent); //todo use activity context without flag?
            }
        };
        holder.authorImage.setOnClickListener(listener);
        holder.authorNameTV.setOnClickListener(listener);
    }

    public void setData(List<Shot> newShots) {
        mShots.clear();
        appendData(newShots);
    }

    public void appendData(List<Shot> newShots) {
        mShots.addAll(newShots);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.shot_item_image)
        SimpleDraweeView shotImage;
        @Bind(R.id.shot_item_author_image)
        SimpleDraweeView authorImage;
        @Bind(R.id.shot_item_like)
        TextView likeTV;
        @Bind(R.id.shot_item_msg)
        TextView msgTV;
        @Bind(R.id.shot_item_view)
        TextView viewTV;
        @Bind(R.id.shot_item_author_name)
        TextView authorNameTV;
        @Bind(R.id.shot_item_title)
        TextView titleTV;
        @Bind(R.id.shot_item_gif_tag)
        TextView gifTag;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
