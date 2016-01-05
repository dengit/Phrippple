package com.dengit.phrippple.ui.comment;

import com.dengit.phrippple.ui.base.FetchBaseModel;

/**
 * Created by dengit on 15/12/14.
 */
public interface CommentModel<T> extends FetchBaseModel<T> {
    void setShotId(int shotId);
}
