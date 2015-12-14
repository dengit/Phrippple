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

/**
 * Created by dengit on 15/12/14.
 */
public class CommentAdapter extends BaseAdapter {
    
    private List<Comment> mComments;

    public CommentAdapter(List<Comment> comments) {
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

            holder = new ViewHolder();
            holder.userPortrait = (SimpleDraweeView) convertView.findViewById(R.id.user_portrait);
            holder.userName = (TextView) convertView.findViewById(R.id.user_name);
            holder.commentContent = (TextView) convertView.findViewById(R.id.comment_content);
            holder.commentTime = (TextView) convertView.findViewById(R.id.comment_time);
            holder.commentLikeCount = (TextView) convertView.findViewById(R.id.comment_like_count);

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


    private static class ViewHolder {
        public SimpleDraweeView userPortrait;
        public TextView userName;
        public TextView commentContent;
        public TextView commentTime;
        public TextView commentLikeCount;
    }
}
