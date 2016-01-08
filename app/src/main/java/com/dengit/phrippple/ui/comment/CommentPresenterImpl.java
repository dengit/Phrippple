package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentPresenterImpl extends FetchBasePresenterImpl<Comment> implements CommentPresenter {
    private CommentView mCommentView;
    private CommentModel mCommentModel;

    public CommentPresenterImpl(CommentView commentView) {
        super(commentView);
        mCommentView = commentView;
        mCommentModel = new CommentModelImpl(this);
        setBaseModel(mCommentModel);
    }

    @Override
    public void firstFetchItems() {
        mCommentModel.setShotId(mCommentView.getShotId());
        super.firstFetchItems();
    }
}
