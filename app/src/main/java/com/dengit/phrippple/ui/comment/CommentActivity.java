package com.dengit.phrippple.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.CommentsAdapter;
import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentActivity extends BaseActivity implements CommentView {

    private CommentPresenter mCommentPresenter;

    @Bind(R.id.comment_list)
    ListView mCommentList;

    private CommentsAdapter mCommentsAdapter;
    private int shotId;

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

    private void initSetup() {
        shotId = getIntent().getIntExtra("shotId", 0);
        int commentCount = getIntent().getIntExtra("commentCount", 0);
        setTitle(commentCount + " comments");
        mCommentPresenter = new CommentPresenterImpl(this);
        mCommentsAdapter = new CommentsAdapter(new ArrayList<Comment>());
        mCommentList.setAdapter(mCommentsAdapter);
        EventBusUtil.getInstance().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCommentPresenter.onResume(shotId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.getInstance().unregister(this);
    }

    @Subscribe
    public void addItems(ArrayList<Comment> comments) {
        mCommentsAdapter.appendData(comments);
    }
}
