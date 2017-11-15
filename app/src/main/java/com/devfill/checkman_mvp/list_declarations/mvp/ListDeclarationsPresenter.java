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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class  ListDeclarationsPresenter extends PresenterBase<ListDeclarationsContract.View> implements ListDeclarationsContract.Presenter {

    private String LOG_TAG = "ListDeclarationsPresenter";

    private ListDeclarationsContract.Model model = new ListDeclarationsModel();
    private List<Declarations.Item> declarationsList = new ArrayList<>();

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
    public void onClickItemDeclarations(int position) {

      /*  String  name =  declarationsList.get(position).getLastname() + " " + declarationsList.get(position).getFirstname(); //достаем имя
        downloadFile(declarationsList.get(position).getLinkPDF(),name); //качаем файл по ссылке
        declarationsList.get(position).getLinkPDF()*/

      //  model.

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

                                declarationsList = declarations.getItems();
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
