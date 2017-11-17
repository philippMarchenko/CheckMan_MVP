package com.devfill.checkman_mvp.dagger;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.devfill.checkman_mvp.dagger.module.view.ListDeclarationsActivityModule;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static AppComponent getComponent() {
        return component;
    }


    public static void createComponent(Activity activity, Context context){

        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(context))
                .listDeclarationsActivityModule(new ListDeclarationsActivityModule(activity))
                .build();
    }
}