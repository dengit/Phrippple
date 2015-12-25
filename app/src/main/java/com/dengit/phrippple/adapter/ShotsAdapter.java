package com.dengit.phrippple.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.TransitionBaseActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotsAdapter extends BaseAdapter {

    private List<Shot> mShots;
    private TransitionBaseActivity<Shot> mActivity;

    public ShotsAdapter(List<Shot> shots, TransitionBaseActivity<Shot> activity) {
        mShots = shots;
        mActivity = activity;
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
            convertView = LayoutInflater.from(APP.getInstance()).inflate(R.layout.item_shot, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setUpShotItem(holder, position);

        return convertView;
    }

    public void setData(List<Shot> newShots) {
        mShots.clear();
        appendData(newShots);
    }

    public void appendData(List<Shot> newShots) {
        mShots.addAll(newShots);
        notifyDataSetChanged();
    }

    private void setUpShotItem(ViewHolder holder, final int position) {
        final Shot shot = (Shot) getItem(position);

        if (shot.user != null) {//self shots when shot.user is null
            if (shot.user.avatar_url.contains("gif")) {
                Timber.d("**avatar url: %s", shot.user.avatar_url);
                Timber.d("**avatar name: %s", shot.user.name);
            }

            holder.authorNameTV.setText(String.valueOf(shot.user.name));
            holder.authorImage.setImageURI(Uri.parse(shot.user.avatar_url));

            //todo new listener every time
            holder.header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDetailActivity(v, position);
                }
            });
        } else {
            holder.authorNameTV.setVisibility(View.GONE);
            holder.authorImage.setVisibility(View.GONE);
            holder.titleTV.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    APP.getInstance().getResources().getDimension(R.dimen.shot_title_big));
        }

        holder.shotImage.setImageURI(Uri.parse(shot.images.normal));
        holder.likeTV.setText(String.valueOf(shot.likes_count));
        holder.msgTV.setText(String.valueOf(shot.comments_count));
        holder.viewTV.setText(String.valueOf(shot.views_count));
        holder.titleTV.setText(String.valueOf(shot.title));

        if (shot.animated) {
            holder.gifTag.setVisibility(View.VISIBLE);
        } else {
            holder.gifTag.setVisibility(View.GONE);
        }
    }

    private void startDetailActivity(View view, int position) {
        Shot shot = (Shot) getItem(position);
        final Intent intent = ProfileActivity.createIntent(shot.user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.shot_item_author_image);

        if (Utils.hasLollipop()) {
            mActivity.startActivityLollipop(image, intent, "photo_hero");
        } else {
            mActivity.startActivityGingerBread(image, intent);
        }
    }

    static class ViewHolder {
        @Bind(R.id.shot_item_image)
        SimpleDraweeView shotImage;
        @Bind(R.id.shot_item_author_image)
        SimpleDraweeView authorImage;
        @Bind(R.id.shot_item_like_count)
        TextView likeTV;
        @Bind(R.id.shot_item_comment_count)
        TextView msgTV;
        @Bind(R.id.shot_item_view_count)
        TextView viewTV;
        @Bind(R.id.shot_item_author_name)
        TextView authorNameTV;
        @Bind(R.id.shot_item_title)
        TextView titleTV;
        @Bind(R.id.shot_item_gif_tag)
        TextView gifTag;
        @Bind(R.id.shot_item_header)
        View header;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
