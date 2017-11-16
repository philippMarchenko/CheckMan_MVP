package com.devfill.checkman_mvp.list_declarations.mvp;


import com.devfill.checkman_mvp.base.mvp.MvpModel;
import com.devfill.checkman_mvp.base.mvp.MvpPresenter;
import com.devfill.checkman_mvp.base.mvp.MvpView;
import com.devfill.checkman_mvp.model_data.Declarations;


import java.util.ArrayList;

import io.reactivex.Observable;

public interface ListDeclarationsContract {

    interface View extends MvpView {

        void showListDeclarations(Declarations declarations);

        void hideDownloadMode();

        void showDeclarationActivity(String name);

        void showMessage(int messageResId);

    }

    interface Presenter extends MvpPresenter<View> {

        void onClickItemDeclarations(int position);

        void getDeclarations(String text);
    }

    interface Model extends MvpModel {


        Observable downloadFile (String url,String name);

        Observable<Declarations> getDeclarations(String name);

    }

}
