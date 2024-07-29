package com.example.projetoestagioaiko.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://aiko-olhovivo-proxy.aikodigital.io/";
    public static Retrofit getClient(String baseUrl) {
            if(retrofit == null){
               retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            }
            return retrofit;
    }

}
