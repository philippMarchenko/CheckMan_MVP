package com.devfill.checkman_mvp.internet;


import com.devfill.checkman_mvp.model.Declarations;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerAPI {

    public static final String BASE_URL = "https://public-api.nazk.gov.ua";

    @GET("/v1/declaration/")
    Call<Declarations> getDeclarationsList(@Query(value = "q") CharSequence q);


}