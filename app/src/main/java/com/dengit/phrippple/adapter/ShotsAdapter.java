package com.dengit.phrippple.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.base.transition.BaseTransitionFetchActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.ui.shot.ShotActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by dengit on 15/12/8.
 */
public class ShotsAdapter extends RecyclerViewAdapter<Shot> {

    @Inject
    public ShotsAdapter() {
        super(new ArrayList<Shot>());
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.item_shot;
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolderItem(View itemView) {
        return new ShotVHItem(itemView);
    }

    protected void setupItems(VHItemBase holder, final int position) {
        ShotVHItem itemHolder = (ShotVHItem) holder;
        final Shot shot = (Shot) getItem(position);

        if (shot.user != null) {//self shots when shot.user is null
            if (shot.user.avatar_url.contains("gif")) {
                Timber.d("**avatar url: %s", shot.user.avatar_url);
                Timber.d("**avatar name: %s", shot.user.name);
            }

            itemHolder.authorNameTV.setText(String.valueOf(shot.user.name));
            itemHolder.authorImage.setImageURI(Uri.parse(shot.user.avatar_url));
        } else {
            itemHolder.header.setVisibility(View.GONE);
            itemHolder.authorNameTV.setVisibility(View.GONE);
            itemHolder.authorImage.setVisibility(View.GONE);
            itemHolder.titleTV.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    APP.getInstance().getResources().getDimension(R.dimen.shot_title_big));
        }

        itemHolder.shotImage.setImageURI(Uri.parse(shot.images.normal));
        itemHolder.likeTV.setText(String.valueOf(shot.likes_count));
        itemHolder.msgTV.setText(String.valueOf(shot.comments_count));
        itemHolder.viewTV.setText(String.valueOf(shot.views_count));
        itemHolder.titleTV.setText(String.valueOf(shot.title));

        if (shot.animated) {
            itemHolder.gifTag.setVisibility(View.VISIBLE);
        } else {
            itemHolder.gifTag.setVisibility(View.GONE);
        }
    }

    public interface OnShotItemClickListener extends OnItemClickListener {
        void onHeaderClick(View view, int position);
    }

    class ShotVHItem extends VHItemBase implements View.OnClickListener {
        View shotItemView;

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

        public ShotVHItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
            shotItemView = view;
            view.setOnClickListener(this);
            header.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                if (shotItemView == v) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                } else if (header == v) {
                    ((OnShotItemClickListener)mItemClickListener)
                            .onHeaderClick(v, getAdapterPosition());
                }
            }
        }
    }

}
