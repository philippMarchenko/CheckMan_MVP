package com.devfill.checkman_mvp.dagger;

import com.devfill.checkman_mvp.base.dagger.ActivityComponentBuilder;
import com.devfill.checkman_mvp.list_declarations.dagger.ListDeclarationsActivityComponent;
import com.devfill.checkman_mvp.list_declarations.mvp.ListDeclarationsActivity;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {ListDeclarationsActivityComponent.class})
public class AppSubComponentsModule {

    @Provides
    @IntoMap
    @ClassKey(ListDeclarationsActivity.class)
    ActivityComponentBuilder provideSplashViewBuilder(ListDeclarationsActivityComponent.Builder builder) {
        return builder;
    }

}
