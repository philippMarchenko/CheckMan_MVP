package com.devfill.checkman_mvp.list_declarations.mvp;


import com.devfill.checkman_mvp.R;
import com.devfill.checkman_mvp.base.mvp.PresenterBase;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


public class  ListDeclarationsPresenter extends PresenterBase<ListDeclarationsContract.View> implements ListDeclarationsContract.Presenter {

    private ListDeclarationsContract.Model model = new ListDeclarationsModel();

    private ListDeclarationsContract.View view;
    private Subscriber subscriber = ;


    public ListDeclarationsPresenter() {

    }

    @Override
    public void viewIsReady() {



    }


    @Override
    public void onClickListItem(int position) {
        if (!subscriber.is()) {
            subscription.unsubscribe();
        }

        subscription = model.getRepoList(view.getUserName())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repo> data) {
                        if (data != null && !data.isEmpty()) {
                            view.showData(data);
                        } else {
                            view.showEmptyList();
                        }
                    }
                });
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }
}
