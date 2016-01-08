package com.dengit.phrippple.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.CommentsAdapter;
import com.dengit.phrippple.adapter.RecyclerViewAdapter;
import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.base.transition.BaseTransitionFetchActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentActivity extends BaseTransitionFetchActivity<Comment> implements CommentView, RecyclerViewAdapter.OnItemClickListener {
    @Inject
    CommentsAdapter mCommentsAdapter;

    @Inject
    CommentPresenter mCommentPresenter;

    private int mShotId;

    public static Intent createIntent(int shotId, int commentCount) {
        Intent intent = new Intent(APP.getInstance(), CommentActivity.class);
        intent.putExtra("shotId", shotId);
        intent.putExtra("commentCount", commentCount);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);

        initSetup();
    }

    @Override
    public int getShotId() {
        return mShotId;
    }

    @Override
    protected void appendAdapterData(List<Comment> newItems) {
        mCommentsAdapter.appendData(newItems);
    }

    @Override
    protected void setAdapterData(List<Comment> newItems) {
        mCommentsAdapter.setData(newItems);
    }

    @Override
    public void onItemClick(View view, int position) {
        startProfileDetailActivity(view, position);
    }

    private void startProfileDetailActivity(View view, int position) {
        Comment comment = (Comment) mCommentsAdapter.getItem(position);
        final Intent intent = ProfileActivity.createIntent(comment.user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startDetailActivity(view, intent, R.id.comment_user_portrait_image);
    }

    private void initSetup() {
        mCommentPresenter.attachView(this);
        setupBase(mCommentPresenter);

        mShotId = getIntent().getIntExtra("shotId", 0);
        int commentCount = getIntent().getIntExtra("commentCount", 0);
        setTitle(commentCount + " comments");

        mCommentsAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mCommentsAdapter);
        mCommentPresenter.firstFetchItems();
    }

}
