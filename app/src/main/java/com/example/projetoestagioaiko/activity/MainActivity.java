package com.example.projetoestagioaiko.activity;

import static com.example.projetoestagioaiko.api.RetrofitClient.BASE_URL;
import static java.util.Locale.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.projetoestagioaiko.R;
import com.example.projetoestagioaiko.adapters.LinhaOnibusAdapter;
import com.example.projetoestagioaiko.adapters.PontoDeParadaAdapter;
import com.example.projetoestagioaiko.api.OlhoVivoApiService;
import com.example.projetoestagioaiko.api.RetrofitClient;
import com.example.projetoestagioaiko.models.LinhaOnibus;
import com.example.projetoestagioaiko.models.PontoDeParada;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPontos;
    private RecyclerView recyclerViewLinhas;
    private RecyclerView recyclerView;
    private PontoDeParadaAdapter pontoDeParadaAdapter;
    private LinhaOnibusAdapter linhasAdapter;
    private List<PontoDeParada> pontoDeParadaList = new ArrayList<>();
    private List<LinhaOnibus> linhaList = new ArrayList<>();
    private List<LinhaOnibus> filteredLinhaList = new ArrayList<>();
    private SearchView  searchView;
    private Spinner spinnerFilters;
    private OlhoVivoApiService olhoVivoApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAbrirMap = findViewById(R.id.button_open_map);
        btnAbrirMap.setOnClickListener(view ->  {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
        });


        recyclerViewPontos = findViewById(R.id.recycler_view_pontos_de_parada);
        recyclerViewPontos.setLayoutManager(new LinearLayoutManager(this));
        pontoDeParadaAdapter = new PontoDeParadaAdapter(pontoDeParadaList);
        recyclerViewPontos.setAdapter(pontoDeParadaAdapter);

        recyclerViewLinhas = findViewById(R.id.recycler_view_linhas);
        recyclerViewLinhas.setLayoutManager(new LinearLayoutManager(this));
        linhasAdapter = new LinhaOnibusAdapter(filteredLinhaList);
        recyclerViewLinhas.setAdapter(linhasAdapter);

        searchView = findViewById(R.id.searchView);
        spinnerFilters = findViewById(R.id.spinnerFilters);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilters.setAdapter(adapter);

        spinnerFilters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter(searchView.getQuery().toString(), parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query, spinnerFilters.getSelectedItem().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText, spinnerFilters.getSelectedItem().toString());
                return true;
            }
        });

        olhoVivoApiService = RetrofitClient.getClient("https://aiko-olhovivo-proxy.aikodigital.io/").create(OlhoVivoApiService.class);
        carregarLinhas();
        obterPontosDeParada();
    }

    private void carregarLinhas() {
        olhoVivoApiService.obterLinhas().enqueue(new Callback<List<LinhaOnibus>>() {
            @Override
            public void onResponse(Call<List<LinhaOnibus>> call, Response<List<LinhaOnibus>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    linhaList.clear();
                    linhaList.addAll(response.body());
                    filteredLinhaList.addAll(linhaList);
                    linhasAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<LinhaOnibus>> call, Throwable t) {
                Log.e("MainActivity", "Erro ao carregar linhas", t);
            }
        });
    }

    private void obterPontosDeParada() {
        olhoVivoApiService.buscarParadas("Afonso").enqueue(new Callback<List<PontoDeParada>>() {
            @Override
            public void onResponse(Call<List<PontoDeParada>> call, Response<List<PontoDeParada>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pontoDeParadaList.clear();
                    pontoDeParadaList.addAll(response.body());
                    pontoDeParadaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PontoDeParada>> call, Throwable t) {
                Log.e("MainActivity", "Erro ao obter pontos de parada", t);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filter(String query, String filter) {
        filteredLinhaList.clear();
        for (LinhaOnibus linha : linhaList) {
            if (linha.getNome().toLowerCase().contains(query.toLowerCase()) &&
                    (filter.equalsIgnoreCase("Todos") || linha.getNome().startsWith(filter))) {
                filteredLinhaList.add(linha);
            }
        }
        linhasAdapter.notifyDataSetChanged();
    }
}