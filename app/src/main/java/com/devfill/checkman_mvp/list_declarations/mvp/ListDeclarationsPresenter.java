package com.devfill.checkman_mvp.list_declarations.mvp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.devfill.checkman_mvp.R;
import com.devfill.checkman_mvp.base.mvp.PresenterBase;
import com.devfill.checkman_mvp.model.Declarations;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class  ListDeclarationsPresenter extends PresenterBase<ListDeclarationsContract.View> implements ListDeclarationsContract.Presenter {

    private String LOG_TAG = "ListDeclarationsPresenter";

    private ListDeclarationsContract.Model model = new ListDeclarationsModel();

    private ListDeclarationsContract.View view;
    private Context mContext;

    public ListDeclarationsPresenter(Context context) {

        mContext = context;

    }

    @Override
    public void viewIsReady() {
        Log.d(LOG_TAG,"viewIsReady ");

    }


    @Override
    public void onClickListItem(int position) {




       /* if (!subscriber.is()) {
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
                });*/


    }

    @Override
    public void getDeclarations(String text) {
        Log.d(LOG_TAG,"onSearchConfirmed ");

        String netType = getNetworkType(mContext);
        if(netType == null){
                 getView().hideDownloadMode();
                 getView().showMessage(R.string.no_internet);
        }
        else {

            model.getDeclarations(text).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Declarations>() {
                        @Override
                        public void onNext(Declarations declarations) {
                            try{
                                getView().showListDeclarations(declarations);
                                getView().hideDownloadMode();
                            }
                            catch(Exception e){
                                getView().showMessage(R.string.no_result);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            //Handle error
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }


    private String getNetworkType(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return activeNetwork.getTypeName();
        }
        return null;
    }
}
