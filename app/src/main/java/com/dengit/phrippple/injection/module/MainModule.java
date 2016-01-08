package com.dengit.phrippple.injection.module;

import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.main.MainPresenter;
import com.dengit.phrippple.ui.main.MainPresenterImpl;
import com.dengit.phrippple.ui.main.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dengit on 15/12/9.
 */
@Module
public class MainModule {

    @Provides
    public MainPresenter providePresenter() {
        return new MainPresenterImpl();
    }
}
