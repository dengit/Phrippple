package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.ui.BasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentPresenterImpl<T> extends BasePresenterImpl<T> implements CommentPresenter<T> {
    private CommentView<T> mCommentView;
    private CommentModel<T> mCommentModel;

    public CommentPresenterImpl(CommentView<T> commentView) {
        super(commentView);
        mCommentView = commentView;
        mCommentModel = new CommentModelImpl<>(this);
        setBaseModel(mCommentModel);
    }

    @Override
    public void firstFetchItems() {
        mCommentModel.setShotId(mCommentView.getShotId());
        super.firstFetchItems();
    }
}
