package com.dengit.phrippple;

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
