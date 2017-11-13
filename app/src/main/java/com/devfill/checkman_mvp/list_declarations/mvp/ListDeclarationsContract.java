package com.devfill.checkman_mvp.list_declarations.mvp;


import com.devfill.checkman_mvp.base.mvp.MvpModel;
import com.devfill.checkman_mvp.base.mvp.MvpPresenter;
import com.devfill.checkman_mvp.base.mvp.MvpView;
import com.devfill.checkman_mvp.model.Declarations;


import java.util.ArrayList;
import java.util.List;

public interface ListDeclarationsContract {

    interface View extends MvpView {

        void showListDeclarations(List<Declarations> declarationsList);

        void hideListDeclarations();

        void setModeSearch(boolean search);
        void restoreSuggestionList();


        // show message to user
        void showMessage(int messageResId);

        // close screen
        void close();
    }


    interface Presenter extends MvpPresenter<View> {

        // field is filled
        void onClickListItem(int position);

        void onSearchConfirmed(CharSequence text);
    }

    interface Model extends MvpModel {

        ArrayList<String> getSuggestionList();
        List<Declarations> getDeclarations(String name);

    }

}
