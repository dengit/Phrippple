package com.dengit.phrippple.ui.comment;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentPresenterImpl implements CommentPresenter {
    CommentView mCommentView;
    CommentModel mCommentModel;

    public CommentPresenterImpl(CommentView commentView) {
        mCommentView = commentView;
        mCommentModel = new CommentModelImpl();
    }

    @Override
    public void onResume(int shotId) {
        mCommentModel.fetchShotComments(shotId);
    }
}
