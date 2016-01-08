package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.base.FetchBasePresenterImpl;

/**
 * Created by dengit on 15/12/14.
 */
public class CommentPresenterImpl extends FetchBasePresenterImpl<Comment> implements CommentPresenter {
    private CommentView mCommentView;
    private CommentModel mCommentModel;

    public CommentPresenterImpl() {
        mCommentModel = new CommentModelImpl(this);
        attachBaseModel(mCommentModel);
    }

    @Override
    public void firstFetchItems() {
        checkAttached();
        mCommentModel.setShotId(mCommentView.getShotId());
        super.firstFetchItems();
    }

    @Override
    public void attachView(CommentView commentView) {
        attachBaseView(commentView);
        mCommentView = commentView;
    }

    public boolean isViewAttached() {
        return mCommentView != null;
    }

    public void checkAttached() {
        if (!isViewAttached()) throw new RuntimeException(
                "Please call "+this.getClass().getSimpleName()+".attachView() before " +
                        "requesting data to the Presenter");
    }
}
