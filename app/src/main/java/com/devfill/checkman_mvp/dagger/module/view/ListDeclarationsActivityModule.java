package com.devfill.checkman_mvp.dagger.module.view;


import android.app.Activity;
import android.support.v4.app.FragmentActivity;

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

    private DeclarationsListProvider declarationsListProvider;

    private SavedFragment savedFragment;

    public ListDeclarationsActivityModule(Activity activity){

        declarationsListProvider = new DeclarationsListProvider(activity);

    }

    @Provides
    @Singleton
    DeclarationsListProvider provideDeclarationsList() {
        return declarationsListProvider;
    }

    public class DeclarationsListProvider {
        FragmentActivity fragmentActivity;

        DeclarationsListProvider(Activity activity){
            fragmentActivity =  (FragmentActivity) activity;

        }

        private List<Declarations.Item> declarationsList = new ArrayList<>();

        public List<Declarations.Item> getDeclarationsList() {

            if (savedFragment != null){
                declarationsList = savedFragment.getDeclarations();
            }

            else{
                savedFragment = new SavedFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .add(savedFragment, "SAVE_FRAGMENT")
                        .commit();
            }


            return declarationsList;
        }

        public void setDeclarationsList(List<Declarations.Item> declarationsList) {
            savedFragment.setDeclarations(declarationsList);
        }
    }
}
