package com.android.desafioaiko;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.desafioaiko.adapters.BusStopAdapter;
import com.android.desafioaiko.model.BusStop;
import com.android.desafioaiko.model.LineInfo;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

public class BusStopInfo extends AppCompatActivity implements OnMapReadyCallback {

    private SearchView searchBar;
    private GridView listOfStops;
    private GoogleMap map;


    /* Método para obter paradas que se enquadrem no termo de busca e adicionar
     * marcadores no mapa para cada um, além de preencher o GridView 'listOfStops'
     */
    private void searchForStops(String query){
        String url = "https://aiko-olhovivo-proxy.aikodigital.io/Parada/Buscar?termosBusca=" + query;
        RequestQueue queue = Volley.newRequestQueue(this);
        Request request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Type busStopType = new TypeToken<List<BusStop>>(){}.getType();
                List<BusStop> busStopList =
                        new Gson().fromJson(String.valueOf(response), busStopType);
                BusStopAdapter busStopAdapter =
                        new BusStopAdapter(BusStopInfo.this, busStopList);
                listOfStops.setAdapter(busStopAdapter);
                map.clear();
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bus_station);
                for(BusStop stop : busStopList){
                    LatLng coord = new LatLng(stop.getPy(), stop.getPx());
                    map.addMarker(new MarkerOptions().position(coord).title(stop.getEd()).icon(icon));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(coord, 13));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bus_stop_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.busStopMapContainer);

        mapFragment.getMapAsync(this);

        searchBar = findViewById(R.id.busStopSearchBar);
        listOfStops = findViewById(R.id.listOfStops);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchForStops(s);
                return true;
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
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.45, -46.63), 13));

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.styles));
    }
}