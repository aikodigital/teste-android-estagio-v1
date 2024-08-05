package com.android.desafioaiko;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
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

import org.json.JSONArray;
import org.json.JSONObject;

public class BusLines extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;

    private TextView txtBusName;

    private TextView txtLineCode;

    private TextView txtException;

    private SearchView searchBar;

    /*
    *
    * Método para coletar dados sobre as linhas de ônibus que
    * estão de acordo com a entrada. Depois da requesição,
    * os dados sobre o titulo da linha e o letreiro(codigo) d
    * o onibus são armazenados nos TextViews respectivos.
    * Depois o método getBusLocation é chamado para pegar
    * dados sobre a localização dos ônibus.
    *
    */
    private void getBusCode(String query){
        String busUrl = "https://aiko-olhovivo-proxy.aikodigital.io/Linha/Buscar?termosBusca=" + query;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonRequest request = new JsonArrayRequest(busUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject candidateLine = response.getJSONObject(0);
                    int code = candidateLine.getInt("cl");
                    String lineTitle =
                            candidateLine.getString("tp") + "/" + candidateLine.getString("ts");
                    txtBusName.setText(lineTitle);
                    String lineCode = candidateLine.getString("lt");
                    txtLineCode.setText(lineCode);
                    getBusLocation(code);
                    txtException.setText("");
                }
                catch (Exception e) {
                    txtException.setText("Não foi possivel achar resultados para a pesquisa");
                    Log.e("ERROR", e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtException.setText("Não foi possível achar resultados para a pesquisa");
                Log.e("ERROR", error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    /*
    *  O método a partir do valor do codigo  da linha obtém
    *  dados sobre a localização e informações do onibus.
    *  Os dados da localização(pY e pX) e titulo da linha são utilizados
    *  para adicionar marcadores no mapa;
    */
    public void getBusLocation(int code){
        String locationUrl =
                "https://aiko-olhovivo-proxy.aikodigital.io/Posicao/Linha?codigoLinha=" + String.valueOf(code);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(locationUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                double pY = 0 , pX = 0;

                try{
                    JSONArray locationArray = response.getJSONArray("vs");
                    map.clear();
                    for(int i = 0; i < locationArray.length(); i++){
                        JSONObject location = locationArray.getJSONObject(i);
                        pY = location.getDouble("py");
                        pX = location.getDouble("px");
                        LatLng coord = new LatLng(pY, pX);
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bus_icon);
                        map.addMarker(new MarkerOptions()
                                .position(coord)
                                .title(txtBusName.getText().toString())
                                .icon(icon));

                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coord, 15));
                    }
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bus_lines);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtBusName = findViewById(R.id.txtBusName);
        txtLineCode = findViewById(R.id.txtLineCode);
        txtException = findViewById(R.id.txtException);
        searchBar = findViewById(R.id.busLineSearch);

        /*
        * Para que seja possível usar o mapa e adicionar modificações,
        * é necessário carregar o Fragment na classe.
         */
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.busLinesMapContainer);

        mapFragment.getMapAsync(BusLines.this);


        // O método de obtenção de dados da API é realizado no evento de query do SearchView
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
        map = googleMap; //set do atributo map
        //adiciona um Zoom sobre os arreadores da cidade de S. Paulo.
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.45, -46.63), 10.5f));

        //adiciona um estilo escuro modificado para o mapa
        map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.styles));

    }
}