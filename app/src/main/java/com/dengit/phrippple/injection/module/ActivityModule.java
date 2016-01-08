package com.dengit.phrippple.injection.module;

import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.bucket.BucketPresenter;
import com.dengit.phrippple.ui.bucket.BucketPresenterImpl;
import com.dengit.phrippple.ui.comment.CommentPresenter;
import com.dengit.phrippple.ui.comment.CommentPresenterImpl;
import com.dengit.phrippple.ui.fan.FanPresenter;
import com.dengit.phrippple.ui.fan.FanPresenterImpl;
import com.dengit.phrippple.ui.main.MainPresenter;
import com.dengit.phrippple.ui.main.MainPresenterImpl;
import com.dengit.phrippple.ui.main.MainView;
import com.dengit.phrippple.ui.shot.ShotPresenter;
import com.dengit.phrippple.ui.shot.ShotPresenterImpl;
import com.dengit.phrippple.ui.shotlist.ShotListPresenter;
import com.dengit.phrippple.ui.shotlist.ShotListPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dengit on 15/12/9.
 */
@Module
public class ActivityModule {

    @Provides
    public MainPresenter provideMainPresenter() {
        return new MainPresenterImpl();
    }

    @Provides
    public BucketPresenter provideBucketPresenter() {
        return new BucketPresenterImpl();
    }

    @Provides
    public CommentPresenter provideCommentPresenter() {
        return new CommentPresenterImpl();
    }

    @Provides
    public FanPresenter provideFanPresenter() {
        return new FanPresenterImpl();
    }

    @Provides
    public ShotListPresenter provideShotListPresenter() {
        return new ShotListPresenterImpl();
    }

    @Provides
    public ShotPresenter provideShotPresenter() {
        return new ShotPresenterImpl();
    }

}
