package com.dengit.phrippple.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dengit.phrippple.APP;
import com.dengit.phrippple.R;
import com.dengit.phrippple.adapter.CommentsAdapter;
import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.data.User;
import com.dengit.phrippple.ui.BaseActivity;
import com.dengit.phrippple.ui.TransitionBaseActivity;
import com.dengit.phrippple.ui.profile.ProfileActivity;
import com.dengit.phrippple.utils.EventBusUtil;
import com.dengit.phrippple.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentActivity extends TransitionBaseActivity<Comment> implements CommentView<Comment>, AdapterView.OnItemClickListener {

    private int mShotId;
    private CommentsAdapter mCommentsAdapter;
    private CommentPresenter<Comment> mCommentPresenter;

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startDetailActivity(view, position);
    }

    private void initSetup() {
        mShotId = getIntent().getIntExtra("shotId", 0);
        int commentCount = getIntent().getIntExtra("commentCount", 0);
        setTitle(commentCount + " comments");
        mCommentPresenter = new CommentPresenterImpl<>(this);
        setBasePresenter(mCommentPresenter);
        mCommentsAdapter = new CommentsAdapter(new ArrayList<Comment>());
        mListView.setAdapter(mCommentsAdapter);
        mListView.setOnItemClickListener(this);

        initBase();
        mCommentPresenter.firstFetchItems();
    }
    /**
     * When the user clicks a thumbnail, bundle up information about it and launch the
     * details activity.
     */
    private void startDetailActivity(View view, int position) {
        Comment comment = (Comment) mCommentsAdapter.getItem(position);
        final Intent intent = ProfileActivity.createIntent(comment.user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.comment_user_portrait_image);

        if (Utils.hasLollipop()) {
            startActivityLollipop(image, intent, "photo_hero");
        } else {
            startActivityGingerBread(image, intent);
        }
    }
}
