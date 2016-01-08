package com.dengit.phrippple.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.CommentsAdapter;
import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.base.transition.BaseTransitionFetchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentActivity extends BaseTransitionFetchActivity<Comment> implements CommentView {

    private int mShotId;
    private CommentsAdapter mCommentsAdapter;
    private CommentPresenter mCommentPresenter;

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

    private void initSetup() {
        mCommentPresenter = new CommentPresenterImpl();
        mCommentPresenter.attachView(this);
        setupBase(mCommentPresenter);

        mShotId = getIntent().getIntExtra("shotId", 0);
        int commentCount = getIntent().getIntExtra("commentCount", 0);
        setTitle(commentCount + " comments");

        mCommentsAdapter = new CommentsAdapter(this);
        mRecyclerView.setAdapter(mCommentsAdapter);
        mCommentPresenter.firstFetchItems();
    }

}
