package com.example.projetoestagioaiko.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authenticator {
    private OlhoVivoApiService apiService;

    public Authenticator(OlhoVivoApiService apiService) {
        this.apiService = apiService;
    }

    public void authenticate(String token, final AuthCallback callback){
        apiService.autenticar(token).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() && response.body() != null && response.body()){
                    callback.onSuccess();
                }else {
                    callback.onFailure(new Exception("Falha na autenticação: "  + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callback.onFailure(new Exception("Erro na autenticação: ", t));

            }
        });

    }

    public interface AuthCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}
