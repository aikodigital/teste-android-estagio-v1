package com.example.projetoestagioaiko.api;


import com.example.projetoestagioaiko.models.LinhaOnibus;
import com.example.projetoestagioaiko.models.PontoDeParada;
import com.example.projetoestagioaiko.models.PontosDeParadaResponse;
import com.example.projetoestagioaiko.models.PrevisaoChegada;
import com.example.projetoestagioaiko.models.Veiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OlhoVivoApiService {
    @POST("Login/Autenticar")
    Call<Boolean> autenticar(@Query("token") String token);

    @GET("Parada/Buscar")
    Call<List<PontoDeParada>> buscarParadas(@Query("termosBusca") String termosBusca);

    @GET("Parada/BuscarParadasPorLinha")
    Call<List<PontoDeParada>> buscarParadasPorLinha(@Query("codigoLinha") int codigoLinha);

    @GET("Posicao")
    Call<List<Veiculo>> obterPosicoesVeiculos();

    @GET("Linha/Buscar")
    Call<List<LinhaOnibus>> obterLinhas();

    @GET("Parada/Buscar")
    Call<PontosDeParadaResponse> obterPontosDeParada();

    @GET("Previsao/Linha")
    Call<List<PrevisaoChegada>> obterPrevisaoChegada(@Query("codigoLinha") String LinhaId);

    @GET("Previsao/Parada")
    Call<List<PrevisaoChegada>> obterPrevisaoChegadaPorParada(@Query("codigoParada") String paradaId);


}
