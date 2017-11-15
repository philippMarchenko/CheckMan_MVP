package com.devfill.checkman_mvp.list_declarations.mvp;


import com.devfill.checkman_mvp.base.mvp.MvpModel;
import com.devfill.checkman_mvp.base.mvp.MvpPresenter;
import com.devfill.checkman_mvp.base.mvp.MvpView;
import com.devfill.checkman_mvp.model.Declarations;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public interface ListDeclarationsContract {

    interface View extends MvpView {

        void showListDeclarations(Declarations declarations);
        void hideDownloadMode();


        // show message to user
        void showMessage(int messageResId);

        // close screen
        void close();
    }


    interface Presenter extends MvpPresenter<View> {

        // field is filled
        void onClickItemDeclarations(int position);

        void getDeclarations(String text);
    }

    interface Model extends MvpModel {


        File downloadFile ();
        ArrayList<String> getSuggestionList();
        Observable<Declarations> getDeclarations(String name);

    }

}
