package com.devfill.checkman_mvp.list_declarations.dagger;

import com.devfill.checkman_mvp.base.dagger.ActivityModule;
import com.devfill.checkman_mvp.list_declarations.mvp.ListDeclarationsContract;
import com.devfill.checkman_mvp.list_declarations.mvp.ListDeclarationsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ListDeclarationsActivityModule implements ActivityModule {


    public ListDeclarationsActivityModule() {

    }

    @ListDeclarationsActivityScope
    @Provides
    ListDeclarationsContract.Presenter provideListDeclarationsPresenter() {

        return new ListDeclarationsPresenter();
    }
}
