package com.devfill.checkman_mvp.list_declarations.mvp;


import com.devfill.checkman_mvp.internet.ServerAPI;
import com.devfill.checkman_mvp.model.Declarations;
import com.devfill.checkman_mvp.storage.Preferences;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListDeclarationsModel implements ListDeclarationsContract.Model{

   // private final Preferences preferences;

    private Retrofit retrofit;
    private ServerAPI serverAPI;

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
    public ArrayList<String> getSuggestionList() {
        return null;
    }

}