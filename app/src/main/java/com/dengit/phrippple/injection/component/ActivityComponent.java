package com.dengit.phrippple.injection.component;

import com.dengit.phrippple.injection.module.MainModule;
import com.dengit.phrippple.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by dengit on 15/12/9.
 */

//todo scope
@Component(
        dependencies = APPComponent.class,
        modules = MainModule.class
)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
