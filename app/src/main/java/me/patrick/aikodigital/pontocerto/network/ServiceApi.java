package me.patrick.aikodigital.pontocerto.network;

import java.util.List;

import me.patrick.aikodigital.pontocerto.model.response.BusPositionResponse;
import me.patrick.aikodigital.pontocerto.model.response.BusStopResponse;
import me.patrick.aikodigital.pontocerto.model.BusStopPositionModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET("/Parada/Buscar?termosBusca=")
    Call<List<BusStopPositionModel>> getStopPositions();

    @GET("/Previsao/Parada")
    Call<BusStopResponse> getStopVehicles(@Query("codigoParada") int code);

    @GET("/Posicao/Linha")
    Call<BusPositionResponse> getBusPositions(@Query("codigoLinha") int codigoLinha);

}
