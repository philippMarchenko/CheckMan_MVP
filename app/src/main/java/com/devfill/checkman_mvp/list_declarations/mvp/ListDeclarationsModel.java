package com.devfill.checkman_mvp.list_declarations.mvp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.devfill.checkman_mvp.internet.ServerAPI;
import com.devfill.checkman_mvp.model_data.Declarations;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.RetryPolicy;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListDeclarationsModel implements ListDeclarationsContract.Model{

   private String LOG_TAG = "ListDeclarationsModel";

    private Context mContext;

    private Retrofit retrofit;
    private ServerAPI serverAPI;

    private ThinDownloadManager downloadManager;

    public ListDeclarationsModel(Context context){

        mContext = context;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ServerAPI.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        serverAPI = retrofit.create(ServerAPI.class);

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

                    downloadManager = new ThinDownloadManager(4);
                    RetryPolicy retryPolicy = new DefaultRetryPolicy();

                    File filesDir = mContext.getExternalFilesDir("");

                    Uri downloadUri = Uri.parse(url);
                    Uri destinationUri = Uri.parse(filesDir + "/" + name + ".pdf");
                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW)
                            .setRetryPolicy(retryPolicy)
                            .setDownloadContext("Download1")
                           // .setStatusListener(new MyDownloadDownloadStatusListenerV1())
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