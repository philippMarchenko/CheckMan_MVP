package com.devfill.checkman_mvp.list_declarations.mvp;


import com.devfill.checkman_mvp.R;
import com.devfill.checkman_mvp.base.mvp.PresenterBase;

public class  ListDeclarationsPresenter extends PresenterBase<ListDeclarationsContract.View> implements ListDeclarationsContract.Presenter {



    public ListDeclarationsPresenter() {

    }

    @Override
    public void viewIsReady() {

       getView().showFirst(R.string.enter_pin);

    }


    @Override
    public void onClickListItem(int position) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }
}
