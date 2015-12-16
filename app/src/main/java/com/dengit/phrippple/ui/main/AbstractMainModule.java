package com.dengit.phrippple.ui.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dengit on 15/12/9.
 */
@Module
public abstract class AbstractMainModule<T> {
    private MainView<T> mMainView;

    public AbstractMainModule(MainView<T> mMainView) {
        this.mMainView = mMainView;
    }

    @Provides
    public MainPresenter<T> providePresenter() {
        return new MainPresenterImpl<T>(mMainView);
    }
}
