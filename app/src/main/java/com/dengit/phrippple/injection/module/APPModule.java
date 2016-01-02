package com.dengit.phrippple.injection.module;

import com.dengit.phrippple.APP;

import dagger.Module;

/**
 * Created by dengit on 15/12/9.
 */
@Module
public class APPModule {
    private APP mAPP;

    public APPModule(APP app) {
        this.mAPP = app;
    }

}
