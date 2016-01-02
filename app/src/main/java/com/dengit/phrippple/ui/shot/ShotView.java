package com.dengit.phrippple.ui.shot;

import java.util.List;

/**
 * Created by dengit on 15/12/14.
 */
public interface ShotView {
    int getShotId();

    void lightenLike();

    void showAco(List<String> shotColors);
}
