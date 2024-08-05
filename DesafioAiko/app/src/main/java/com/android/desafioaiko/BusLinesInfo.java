package com.android.desafioaiko;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.desafioaiko.adapters.BusLineInfoAdapter;
import com.android.desafioaiko.model.LineInfo;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BusLinesInfo extends AppCompatActivity {

    private SearchView searchBar;
    private GridView lineInfoList;


    /*
    * Método para obter dados da API e preencher de acordo
    * com o modelo o GridView chamado de 'lineInfoList'.
    *
     */
    public void searchAndFillGrid(String query){
        RequestQueue queue = Volley.newRequestQueue(this);
        String searchUrl =
                "https://aiko-olhovivo-proxy.aikodigital.io/Linha/Buscar?termosBusca=" + query;


        Request request = new JsonArrayRequest(searchUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{

                    ArrayList<LineInfo> lines = new ArrayList<>();
                    Type lineInfoType = new TypeToken<List<LineInfo>>(){}.getType();
                    lines = new Gson().fromJson(String.valueOf(response), lineInfoType);
                    Log.d("RESPONSE", lines.toString());
                    BusLineInfoAdapter adapter = new BusLineInfoAdapter(BusLinesInfo.this, lines);
                    lineInfoList.setAdapter(adapter);
                }
                catch(Exception e){
                    Log.e("ERROR", e.getLocalizedMessage());
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
        setContentView(R.layout.activity_bus_lines_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchBar = (SearchView) findViewById(R.id.InfoSearchBar);
        lineInfoList = (GridView) findViewById(R.id.lineInfoList);

        /* Para ter uma experiência mais agradavel, é feito uma chamada do método na
        * inicialização para preencher o espaço vazio.
         */
        searchAndFillGrid("1");

        // A chamada normal do método é feito no Event Listener do SearchView
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchAndFillGrid(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}