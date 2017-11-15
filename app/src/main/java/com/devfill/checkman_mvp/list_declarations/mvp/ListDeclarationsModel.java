package com.devfill.checkman_mvp.list_declarations.mvp;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.devfill.checkman_mvp.internet.ServerAPI;
import com.devfill.checkman_mvp.model.Declarations;
import com.devfill.checkman_mvp.storage.Preferences;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.RetryPolicy;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Subscriber;
import rx.functions.Func1;

public class ListDeclarationsModel implements ListDeclarationsContract.Model{

   // private final Preferences preferences;
   private String LOG_TAG = "ListDeclarationsModel";

    private Context mContext;

    private Retrofit retrofit;
    private ServerAPI serverAPI;

    File filePath;

    public ListDeclarationsModel(){

       // this.preferences = preferences;

       

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
        return serverAPI.getDeclarationsList(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public File downloadFile() {
        return null;
    }

    @Override
    public ArrayList<String> getSuggestionList() {
        return null;
    }



    private Func1<Response<ResponseBody>, Observable<File>> processResponse() {
        return new Func1<Response<ResponseBody>, Observable<File>>() {
            @Override
            public Observable<File> call(Response<ResponseBody> responseBodyResponse) {
                return saveToDiskRx(responseBodyResponse);
            }
        };
    }

    private Observable<File> saveToDiskRx(final Response<ResponseBody> response) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                try {
                    String header = response.headers().get("Content-Disposition");
                    String filename = header.replace("attachment; filename=", "");

                //    new File("/data/data/" + getPackageName() + "/games").mkdir();
                    File destinationFile = new File("/data/data/" + "" + "/games/" + filename);

                    BufferedSink bufferedSink = Okio.buffer(Okio.sink(destinationFile));
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();

                    subscriber.onNext(destinationFile);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observer<File> handleResult() {
        return new Observer<File>() {

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "Error " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(File file) {
                Log.d(LOG_TAG, "File downloaded to " + file.getAbsolutePath());
            }
        };
    }

    public <T> T createService(Class<T> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return retrofit.create(serviceClass);
    }

}