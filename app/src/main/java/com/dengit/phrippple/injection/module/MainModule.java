package com.dengit.phrippple.injection.module;

import com.dengit.phrippple.data.Shot;
import com.dengit.phrippple.ui.main.MainView;

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
