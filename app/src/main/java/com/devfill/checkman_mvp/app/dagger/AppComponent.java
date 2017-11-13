package com.devfill.checkman_mvp.app.dagger;

import dagger.Component;
import com.devfill.checkman_mvp.app.ComponentsHolder;
import com.devfill.checkman_mvp.dagger.AppSubComponentsModule;
import com.devfill.checkman_mvp.storage.Preferences;

@AppScope
@Component(modules = {AppModule.class, AppSubComponentsModule.class})
public interface AppComponent {
    void injectComponentsHolder(ComponentsHolder componentsHolder);
    Preferences getPreferences();
}
