package com.dengit.phrippple.ui.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dengit on 15/12/9.
 */
@Module
public class MainModule {
    private MainView mMainView;

    public MainModule(MainView mMainView) {
        this.mMainView = mMainView;
    }

    @Provides
    public MainPresenter providePresenter() {
        return new MainPresenterImpl(mMainView);
    }
}
