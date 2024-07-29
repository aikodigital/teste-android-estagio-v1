package com.android.desafioaiko;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.desafioaiko.adapters.ConsultAdapter;
import com.android.desafioaiko.adapters.ScheduleAdapter;
import com.android.desafioaiko.model.Schedule;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BusSchedule extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private GridView listOfSchedule;
    private SearchView searchBar;



    /*
    * Obtém dados sobre os onibus de uma linha, selecionando apenas a primeira
    * que pode estar mais proxima do que foi pedido, executando o método SearchForStops com
    * o codigo da linha.
    *
     */
    private void getBusCode(String query){
        String busCodeUrl =
                "https://aiko-olhovivo-proxy.aikodigital.io/Linha/Buscar?termosBusca=" + query;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(busCodeUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    int busCode = response.getJSONObject(0).getInt("cl");
                    searchForStops(busCode);
                }
                catch(Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    /*
    * A partir da obtenção de dados sobre paradas, adiciona marcadores
    * em suas respectivas localizações.
     */
    public void searchForStops(int code){
        String stopsUrl =
                "https://aiko-olhovivo-proxy.aikodigital.io/Parada/BuscarParadasPorLinha?codigoLinha=" +
                        String.valueOf(code);

        RequestQueue queue = Volley.newRequestQueue(this);

        Request request = new JsonArrayRequest(stopsUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                map.clear();
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bus_station);
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject stopObject = response.getJSONObject(i);
                        LatLng coord =
                                new LatLng(stopObject.getDouble("py"), stopObject.getDouble("px"));

                        map.addMarker(new MarkerOptions()
                                .position(coord)
                                .title(stopObject.getString("ed"))
                                .icon(icon)
                                .snippet(String.valueOf(stopObject.getInt("cp"))));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coord, 15));
                    }

                    catch (JSONException e) {
                        Log.e("ERROR", e.getLocalizedMessage());
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    /*
    * Método para obter previsões de onibus em uma parada,
    *  e preencher o GridView 'listOfSchedule'
     */
    public void getBusStopSchedule(int code){
        String url =
                "https://aiko-olhovivo-proxy.aikodigital.io/Previsao/Parada?codigoParada=" +
                        String.valueOf(code);
        RequestQueue queue = Volley.newRequestQueue(this);
        Request request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Schedule> scheduleList = new ArrayList<>();
                try {
                    JSONObject consultObject = response.getJSONObject("p");
                    JSONArray scheduleArray = consultObject.getJSONArray("l");
                    Type scheduleType = new TypeToken<List<Schedule>>(){}.getType();
                    scheduleList = new Gson().fromJson(String.valueOf(scheduleArray), scheduleType);
                }
                catch (Exception e){
                    Log.e("ERROR", e.getLocalizedMessage());
                }
                finally {
                    ConsultAdapter consultAdapter =
                            new ConsultAdapter(BusSchedule.this, scheduleList);
                    listOfSchedule.setAdapter(consultAdapter);
                    listOfSchedule.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bus_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchBar = (SearchView) findViewById(R.id.scheduleSearchBar);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.scheduleMapContainer);

        mapFragment.getMapAsync(this);

        listOfSchedule = (GridView) findViewById(R.id.listOfSchedule);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getBusCode(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.styles));

        // Aplica zoom sobre a cidade de S. Paulo
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.45, -46.63), 13));

        // Se o usuário clicar em um marcador de pontos, o método será chamado.
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                int stopCode = Integer.parseInt(marker.getSnippet());
                Log.i("SNIPPET", marker.getSnippet());
                getBusStopSchedule(stopCode);
                return false;
            }
        });
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                listOfSchedule.setVisibility(View.GONE);
            }
        });
    }
}