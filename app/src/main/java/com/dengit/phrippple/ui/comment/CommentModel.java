package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.data.Comment;
import com.dengit.phrippple.ui.base.FetchBaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface CommentModel extends FetchBaseModel<Comment> {
    void setShotId(int shotId);
}
