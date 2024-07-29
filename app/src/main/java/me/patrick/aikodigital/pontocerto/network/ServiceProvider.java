package me.patrick.aikodigital.pontocerto.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {

    private static final String API_URL = "https://aiko-olhovivo-proxy.aikodigital.io";
    private static final String API_TOKEN = "24598693b866d61affd4379027baaf0f4d7e09dc77127c6332c553504e00c24f";

    private static Retrofit retrofit;

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS);

    private static OkHttpClient getHttpClient(final String token) {
        if (token != null) {
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", token);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        return httpClientBuilder.build();
    }

    public static Retrofit getRetrofitInstance(String token) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient(token))
                    .build();
        }
        return retrofit;
    }

    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofitInstance(API_TOKEN).create(serviceClass);
    }
}
