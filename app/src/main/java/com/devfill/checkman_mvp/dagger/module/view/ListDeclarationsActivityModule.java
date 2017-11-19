package com.devfill.checkman_mvp.dagger.module.view;


import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.devfill.checkman_mvp.dagger.App;
import com.devfill.checkman_mvp.list_declarations.mvp.presenter.AppDeclarationsPresenter;
import com.devfill.checkman_mvp.list_declarations.mvp.view.helper.SavedFragment;
import com.devfill.checkman_mvp.model_data.Declarations;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module

public class ListDeclarationsActivityModule {

    SavedFragment savedFragment;

    FragmentActivity fragmentActivity;

    public ListDeclarationsActivityModule(Activity activity) {
        fragmentActivity = (FragmentActivity) activity;
    }


    @Provides
    @Singleton
    SavedFragment providesavedFragment() {
        Log.d("ListDeclarationsActivityModule","DeclarationsListProvider");

         savedFragment = (SavedFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag("SAVE_FRAGMENT");


        if (savedFragment != null){
             Log.d("ListDeclarationsActivityModule","savedFragment not null");

             return savedFragment;
         }

            else {
             Log.d("ListDeclarationsActivityModule","savedFragment null");
             savedFragment = new SavedFragment();
             fragmentActivity.getSupportFragmentManager().beginTransaction()
                     .add(savedFragment, "SAVE_FRAGMENT")
                     .commit();

             return savedFragment;
         }
    }

    @Provides
    @Singleton
    DeclarationsListProvider provideDeclarationsList(SavedFragment savedFragment) {

        return new DeclarationsListProvider(savedFragment);
    }

    public class DeclarationsListProvider {

        SavedFragment mSavedFragment;

        public DeclarationsListProvider(SavedFragment savedFragment){

            mSavedFragment = savedFragment;
        }

        public List<Declarations.Item> getDeclarationsList() {

            Log.d("ListDeclarationsActivityModule","DeclarationsListProvider declarationsList");
            return mSavedFragment.getDeclarations();
        }

        public void setDeclarationsList(List<Declarations.Item> declarationsList) {
           /* savedFragment.setDeclarations(declarationsList);*/
            mSavedFragment.setDeclarations(declarationsList);
        }
    }
}
