package com.example.projetoestagioaiko.activity;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoestagioaiko.adapters.PontoDeParadaAdapter;
import com.example.projetoestagioaiko.api.Authenticator;
import com.example.projetoestagioaiko.api.OlhoVivoApiService;
import com.example.projetoestagioaiko.R;
import com.example.projetoestagioaiko.api.RetrofitClient;
import com.example.projetoestagioaiko.models.PontoDeParada;
import com.example.projetoestagioaiko.models.PontosDeParadaResponse;
import com.example.projetoestagioaiko.models.Veiculo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private OlhoVivoApiService olhoVivoApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        olhoVivoApiService = RetrofitClient.getClient().create(OlhoVivoApiService.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        exibirPontosDeParada();
        exibirPosicoesVeiculos();
    }

    private void exibirPontosDeParada() {
        olhoVivoApiService.obterPontosDeParada().enqueue(new Callback<PontosDeParadaResponse>() {
            @Override
            public void onResponse(Call<PontosDeParadaResponse> call, Response<PontosDeParadaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (PontoDeParada ponto : response.body().getPontosDeParada()) {
                        LatLng location = new LatLng(ponto.getLatitude(), ponto.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(location).title(ponto.getNome()));
                    }
                }
            }

            @Override
            public void onFailure(Call<PontosDeParadaResponse> call, Throwable t) {
                Log.e("MapActivity", "Erro ao obter pontos de parada", t);
            }
        });
    }

    private void exibirPosicoesVeiculos() {
        olhoVivoApiService.obterPosicoesVeiculos().enqueue(new Callback<List<Veiculo>>() {
            @Override
            public void onResponse(Call<List<Veiculo>> call, Response<List<Veiculo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Veiculo veiculo : response.body()) {
                        LatLng location = new LatLng(veiculo.getLatitude(), veiculo.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(location).title("Veículo"));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Veiculo>> call, Throwable t) {
                Log.e("MapActivity", "Erro ao obter posições dos veículos", t);
            }
        });
    }
}
