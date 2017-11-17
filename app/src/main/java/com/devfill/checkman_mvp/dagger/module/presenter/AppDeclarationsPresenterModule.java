package com.devfill.checkman_mvp.dagger.module.presenter;

import com.devfill.checkman_mvp.list_declarations.mvp.presenter.AppDeclarationsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppDeclarationsPresenterModule {

    @Provides
    @Singleton
    AppDeclarationsPresenter provideAppDeclarationsPresenter() {
        return new AppDeclarationsPresenter();

    }
}
