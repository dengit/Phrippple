package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.base.FetchBasePresenter;

/**
 * Created by dengit on 15/12/14.
 */
public interface CommentPresenter extends FetchBasePresenter<Comment> {
    void attachView(CommentView commentView);
}
