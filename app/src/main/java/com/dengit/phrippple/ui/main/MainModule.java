package com.dengit.phrippple.ui.main;

import com.dengit.phrippple.data.Shot;

import dagger.Module;

/**
 * Created by dengit on 15/12/9.
 */
@Module
public class MainModule extends AbstractMainModule<Shot> {
    public MainModule(MainView<Shot> mMainView) {
        super(mMainView);
    }
}
