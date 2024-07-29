package com.example.myapplication;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.TravelMode;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapa;
    private GoogleMap googleMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapa = findViewById(R.id.mapa);
        mapa.onCreate(savedInstanceState);
        mapa.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap maps) {
        googleMaps = maps;
        desenhaRota(new LatLng(37.4220, -122.0841), new LatLng(34.0522, -118.2437));
        adicionaParada(new LatLng(-33.852, 151.211));
    }

    private void adicionaParada(LatLng parada) {
        googleMaps.addMarker(new MarkerOptions().position(parada));
    }

    private void desenhaRota(LatLng origem, LatLng destino) {
        final String apiKey = "AIzaSyDUgAFpTJWBvNuWVrSM2ISxAj_M3OAA8OU";

        GeoApiContext contexto = new GeoApiContext.Builder().apiKey(apiKey).build();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                com.google.maps.model.DirectionsResult resultado = DirectionsApi.newRequest(contexto)
                        .mode(TravelMode.DRIVING)
                        .origin(new com.google.maps.model.LatLng(origem.latitude, origem.longitude))
                        .destination(new com.google.maps.model.LatLng(destino.latitude, destino.longitude))
                        .await();

                List<LatLng> caminho = PolyUtil.decode(resultado.routes[0].overviewPolyline.getEncodedPath());

                runOnUiThread(() -> googleMaps.addPolyline(new PolylineOptions()
                        .addAll(caminho)
                        .width(10f)
                        .color(Color.BLUE)));
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao desenhar rota", Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapa.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapa.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapa.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapa.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapa.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapa.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle EstadoExterno) {
        super.onSaveInstanceState(EstadoExterno);
        mapa.onSaveInstanceState(EstadoExterno);
    }
}
