package com.devfill.checkman_mvp.list_declarations.mvp.model;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.devfill.checkman_mvp.dagger.App;
import com.devfill.checkman_mvp.internet.ServerAPI;
import com.devfill.checkman_mvp.list_declarations.mvp.AppDeclarationsContract;
import com.devfill.checkman_mvp.model_data.Declarations;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.RetryPolicy;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class AppDeclarationsModel implements AppDeclarationsContract.Model {

    private String LOG_TAG = "AppDeclarationsModel";

    private Context mContext;

    @Inject
    ServerAPI serverAPI;

    public AppDeclarationsModel(){

        mContext = App.getComponent().getContext();

        serverAPI = App.getComponent().getServerAPI();

    }

    @Override
    public Observable<Declarations> getDeclarations(String name) {
        return serverAPI.getDeclarationsList(name);
    }

    @Override
    public Observable downloadFile(final String url,final String name) {

        Observable downloadObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                try{
                    Log.d(LOG_TAG,"init download... ");

                    ThinDownloadManager downloadManager = new ThinDownloadManager(4);
                    RetryPolicy retryPolicy = new DefaultRetryPolicy();

                    File filesDir = mContext.getExternalFilesDir("");

                    Uri downloadUri = Uri.parse(url);
                    Uri destinationUri = Uri.parse(filesDir + "/" + name + ".pdf");
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW)
                            .setRetryPolicy(retryPolicy)
                            .setDownloadContext("Download1")
                            .setStatusListener(new DownloadStatusListenerV1() {
                                @Override
                                public void onDownloadComplete(DownloadRequest downloadRequest) {

                                    emitter.onComplete();
                                }

                                @Override
                                public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {

                                }

                                @Override
                                public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {
                                    emitter.onNext(progress);
                                }
                            });

                    downloadManager.add(downloadRequest);
                }
                catch (Exception e ){
                    Log.d(LOG_TAG,"error init download  " + e.getMessage());

                    emitter.onError(e);
                }
            }
        });

        return downloadObservable;
    }

}