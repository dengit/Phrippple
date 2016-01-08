package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.base.FetchBaseView;

/**
 * Created by dengit on 15/12/14.
 */
public interface CommentView extends FetchBaseView<Comment> {
    int getShotId();
}
