package com.dengit.phrippple.ui.main;

import dagger.Component;

/**
 * Created by dengit on 15/12/9.
 */

//todo scope
@Component(
        modules = MainModule.class
)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
