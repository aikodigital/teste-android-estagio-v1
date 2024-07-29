package com.example.bustrackingsp.api.utils;

import androidx.annotation.NonNull;

import com.example.bustrackingsp.api.entities.Linha;
import com.example.bustrackingsp.api.entities.Parada;
import com.example.bustrackingsp.api.entities.Posicao;
import com.example.bustrackingsp.api.entities.Previsao;
import com.example.bustrackingsp.mapUtils.MapUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiRequest {
    private static String baseUrl = "https://api.olhovivo.sptrans.com.br/v2.1";
    private static String apiKey = "38864fb77bafe6d2c2bcc078dc05829164268da2a317e340ab8468c9bf3decb0";

    private static String cookieHeader;

    public static void login() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder().url(baseUrl + "/Login/Autenticar?token=" + apiKey)
                .method("POST", body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Erro Login");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    for (String headerName : response.headers().names()) {
                        // Verifica se o cabeçalho é set-cookie
                        if (headerName.equalsIgnoreCase("set-cookie")) {
                            // Obtém todos os valores do cabeçalho "set-cookie"
                            List<String> cookieHeaders = response.headers(headerName);

                            StringBuilder header = new StringBuilder();
                            for (String cookie : cookieHeaders) {
                                header.append(cookie);
                            }
                            cookieHeader = header.toString();
                        }
                    }
                }
            }
        });
    }

    public static void request(String endpoint) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .get()
                .addHeader("Cookie", cookieHeader)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println("Erro Linha");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body().string();
                Gson gson = new Gson();

                if(endpoint.startsWith("/Posicao")){
                    Posicao posicao = gson.fromJson(res, Posicao.class);
                    MapUtils.setLastMapMode(1);
                    MapUtils.setPosicao(posicao);
                    MapUtils.setVehiclesPosMarkers();

                } else if (endpoint.startsWith("/Parada")) {
                    Type classType = new TypeToken<List<Parada>>(){}.getType();
                    List<Parada> paradas = gson.fromJson(res, classType);
                    MapUtils.setLastMapMode(2);
                    MapUtils.setParadas(paradas);

                } else if (endpoint.startsWith("/Linha")) {
                    Type classType = new TypeToken<List<Linha>>(){}.getType();
                    List<Linha> linhas = gson.fromJson(res, classType);
                    MapUtils.setLinhas(linhas);
                    MapUtils.setLastTextMode(1);

                } else if (endpoint.startsWith("/Previsao")) {
                    Previsao previsao = gson.fromJson(res, Previsao.class);
                    MapUtils.setPrevisao(previsao);
                    MapUtils.setLastTextMode(2);
                }

            }
        });
    }
}
