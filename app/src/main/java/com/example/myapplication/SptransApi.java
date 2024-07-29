package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SptransApi {
    @GET("olhovivo/rota")
    Call<RotaResponse> getRota(@Query("id_rota") String idRota);
    @POST("/Login/Autenticar/")
    Call<RotaResponse> postAutentica();
}
