package com.devfill.checkman_mvp.dagger;

import android.content.Context;

import com.devfill.checkman_mvp.dagger.module.model.ServerAPIModule;
import com.devfill.checkman_mvp.dagger.module.presenter.AppDeclarationsPresenterModule;
import com.devfill.checkman_mvp.dagger.module.view.ListDeclarationsActivityModule;
import com.devfill.checkman_mvp.internet.ServerAPI;
import com.devfill.checkman_mvp.list_declarations.mvp.presenter.AppDeclarationsPresenter;
import com.devfill.checkman_mvp.model_data.Declarations;

import java.util.List;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ServerAPIModule.class,
                     AppDeclarationsPresenterModule.class,
                     ContextModule.class,
                     ListDeclarationsActivityModule.class})

public interface AppComponent {

    Context getContext();

    ServerAPI getServerAPI();

    AppDeclarationsPresenter getAppDeclarationsPresenter();

    ListDeclarationsActivityModule.DeclarationsListProvider getDeclarationsProvider ();

    //void setDeclarationsList(List<Declarations.Item> items);

}