package com.devfill.checkman_mvp.list_declarations.dagger;

import com.devfill.checkman_mvp.base.dagger.ActivityComponent;
import com.devfill.checkman_mvp.base.dagger.ActivityComponentBuilder;
import com.devfill.checkman_mvp.list_declarations.mvp.ListDeclarationsActivity;

import dagger.Subcomponent;


@ListDeclarationsActivityScope
@Subcomponent(modules = ListDeclarationsActivityModule.class)
public interface ListDeclarationsActivityComponent  extends ActivityComponent<ListDeclarationsActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<ListDeclarationsActivityComponent, ListDeclarationsActivityModule> {

    }
}
