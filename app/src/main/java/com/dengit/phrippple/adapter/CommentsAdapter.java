package com.dengit.phrippple.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.base.transition.TransitionBaseActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentsAdapter extends RecyclerViewTransitionBaseAdapter<Comment> {

    public CommentsAdapter(List<Comment> comments, View footer, TransitionBaseActivity<Comment> activity) {
        super(comments, footer, activity);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolderItem(View itemView) {
        return new CommentVHItem(itemView);
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.item_comment;
    }

    protected void setUpItems(VHItemBase holder, final int position) {
        CommentVHItem itemHolder = (CommentVHItem) holder;
        Comment comment = (Comment) getItem(position);
        itemHolder.userPortrait.setImageURI(Uri.parse(comment.user.avatar_url));
        itemHolder.userName.setText(comment.user.name);
        itemHolder.commentContent.setText(Utils.textToHtml(comment.body).toString().trim());
        itemHolder.commentTime.setText(comment.updated_at);
        itemHolder.commentLikeCount.setText(String.valueOf(comment.likes_count));

        itemHolder.commentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProfileDetailActivity(v, position);
            }
        });
    }

    private void startProfileDetailActivity(View view, int position) {
        Comment comment = (Comment) getItem(position);
        final Intent intent = ProfileActivity.createIntent(comment.user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startDetailActivity(view, intent, R.id.comment_user_portrait_image);
    }

    static class CommentVHItem extends VHItemBase {
        View commentItemView;

        @Bind(R.id.comment_user_portrait_image)
        SimpleDraweeView userPortrait;
        @Bind(R.id.comment_user_name)
        TextView userName;
        @Bind(R.id.comment_content)
        TextView commentContent;
        @Bind(R.id.comment_pretty_time)
        TextView commentTime;
        @Bind(R.id.comment_like_count)
        TextView commentLikeCount;

        public CommentVHItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
            commentItemView = view;
        }
    }
}
