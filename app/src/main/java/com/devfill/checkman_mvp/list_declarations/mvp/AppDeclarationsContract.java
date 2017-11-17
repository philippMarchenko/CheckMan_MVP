package com.devfill.checkman_mvp.list_declarations.mvp;


import com.devfill.checkman_mvp.base.mvp.MvpModel;
import com.devfill.checkman_mvp.base.mvp.MvpPresenter;
import com.devfill.checkman_mvp.base.mvp.MvpView;
import com.devfill.checkman_mvp.model_data.Declarations;

import io.reactivex.Observable;

public interface AppDeclarationsContract {

    interface View extends MvpView {

        void showListDeclarations(Declarations declarations);      //покажем список деклараций

        void hideDownloadMode();                                   //спрячем прогресбар

        void showDeclarationActivity(String name);                 //запуск активити с декларацией

        void showMessage(int messageResId);                        //покажем тоаст

    }

    interface Presenter extends MvpPresenter<View> {

        void onClickItemDeclarations(int position);                //клик по списку деклараций

        void getDeclarations(String text);                         //запрос на получение списка деклараций у презентера
    }

    interface Model extends MvpModel {

        Observable downloadFile (String url,String name);           //запрос на загрузку файла по урл

        Observable<Declarations> getDeclarations(String name);      //запрос на получение списка деклараций у модели

    }

}
