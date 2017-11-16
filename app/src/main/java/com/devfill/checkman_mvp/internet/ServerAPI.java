package com.devfill.checkman_mvp.internet;


import com.devfill.checkman_mvp.model_data.Declarations;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerAPI {

    public static final String BASE_URL = "https://public-api.nazk.gov.ua";

    @GET("/v1/declaration/")
    Observable<Declarations> getDeclarationsList(@Query(value = "q") CharSequence q);


}