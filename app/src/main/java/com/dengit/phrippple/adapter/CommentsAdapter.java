package com.dengit.phrippple.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.utils.Util;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentsAdapter extends BaseAdapter {
    
    private List<Comment> mComments;

    public CommentsAdapter(List<Comment> comments) {
        mComments = comments;
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(APP.getInstance()).inflate(R.layout.comment_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setUpCommentItem(holder, position);

        return convertView;
    }

    private void setUpCommentItem(ViewHolder holder, int position) {
        Comment comment = (Comment) getItem(position);
        holder.userPortrait.setImageURI(Uri.parse(comment.user.avatar_url));
        holder.userName.setText(comment.user.name);
        holder.commentContent.setText(Util.textToHtml(comment.body).toString().trim());
        holder.commentTime.setText(comment.updated_at);
        holder.commentLikeCount.setText(String.valueOf(comment.likes_count));
    }



    public void setData(List<Comment> newShots) {
        mComments.clear();
        appendData(newShots);
    }

    public void appendData(List<Comment> newShots) {
        mComments.addAll(newShots);
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @Bind(R.id.user_portrait)
        SimpleDraweeView userPortrait;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.comment_content)
        TextView commentContent;
        @Bind(R.id.comment_time)
        TextView commentTime;
        @Bind(R.id.comment_like_count)
        TextView commentLikeCount;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
