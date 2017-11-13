package com.devfill.checkman_mvp.internet;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devfill.checkman_mvp.model.Declarations;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestManager {

    private Retrofit retrofit;
    private ServerAPI serverAPI;


    public Declarations getDeclarationsList(String name) {

        try {

            serverAPI.getDeclarationsList(name).enqueue(new Callback<Declarations>() {
                @Override
                public void onResponse(Call<Declarations> call, Response<Declarations> response) {

                    Declarations declarations = response.body();
                    return declarations;
                }

                @Override
                public void onFailure(Call<Declarations> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
        return null;
    }
}