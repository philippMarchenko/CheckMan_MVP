package com.devfill.checkman_mvp.dagger;
import android.app.Activity;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity){

        this.activity = activity;
    }

    @Provides
    @Singleton
    Activity provideActivity() {
        return activity;
    }
}
