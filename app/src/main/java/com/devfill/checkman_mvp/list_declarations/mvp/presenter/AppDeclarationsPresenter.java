package com.devfill.checkman_mvp.list_declarations.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.devfill.checkman_mvp.R;
import com.devfill.checkman_mvp.base.mvp.PresenterBase;
import com.devfill.checkman_mvp.dagger.App;
import com.devfill.checkman_mvp.list_declarations.mvp.AppDeclarationsContract;
import com.devfill.checkman_mvp.list_declarations.mvp.model.AppDeclarationsModel;
import com.devfill.checkman_mvp.model_data.Declarations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AppDeclarationsPresenter extends PresenterBase<AppDeclarationsContract.View> implements AppDeclarationsContract.Presenter {

    private String LOG_TAG = "AppDeclarationsPresenter";

    private AppDeclarationsContract.Model model;
    private List<Declarations.Item> declarationsList = new ArrayList<>();
    private Context mContext;

    public AppDeclarationsPresenter() {

        mContext =  App.getComponent().getContext();
        model = new AppDeclarationsModel();
    }

    @Override
    public void viewIsReady() {
        Log.d(LOG_TAG,"viewIsReady ");

    }

    @Override
    public void onClickItemDeclarations(int position) {

        final String  name =  declarationsList.get(position).getLastname() + " " + declarationsList.get(position).getFirstname(); //достаем имя
        String  url = declarationsList.get(position).getLinkPDF();

        model.downloadFile(url,name).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer value) {

                        Log.d(LOG_TAG,"onNext  persent " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                    }

                    @Override
                    public void onComplete() {
                        Log.d(LOG_TAG,"Load file finish! ");

                        getView().hideDownloadMode();
                        getView().showDeclarationActivity(name);
                    }
                });

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
                            Log.d(LOG_TAG,"onNext getDeclarations ");

                            try{
                                getView().showListDeclarations(declarations);

                                Log.d(LOG_TAG,"size" + declarations.getItems().size());

                                declarationsList = declarations.getItems();
                            }
                            catch(Exception e){
                                getView().showMessage(R.string.no_result);
                                Log.d(LOG_TAG,"error  getDeclarations" + e.getMessage());

                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(LOG_TAG,"onError getDeclarations " + e.getMessage());

                        }

                        @Override
                        public void onComplete() {
                            Log.d(LOG_TAG,"onComplete getDeclarations ");
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
