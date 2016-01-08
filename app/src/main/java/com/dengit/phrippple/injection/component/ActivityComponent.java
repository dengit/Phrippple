package com.dengit.phrippple.injection.component;

import com.dengit.phrippple.data.Bucket;
import com.dengit.phrippple.injection.module.ActivityModule;
import com.dengit.phrippple.ui.bucket.BucketActivity;
import com.dengit.phrippple.ui.comment.CommentActivity;
import com.dengit.phrippple.ui.fan.FanActivity;
import com.dengit.phrippple.ui.main.MainActivity;
import com.dengit.phrippple.ui.shotlist.ShotListActivity;

import dagger.Component;

/**
 * Created by dengit on 15/12/9.
 */

//todo scope
@Component(
        dependencies = APPComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(FanActivity fanActivity);

    void inject(BucketActivity bucketActivity);

    void inject(CommentActivity commentActivity);

    void inject(ShotListActivity shotListActivity);
}
