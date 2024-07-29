package com.example.projetoestagioaiko.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoestagioaiko.R;
import com.example.projetoestagioaiko.adapters.PrevisaoChegadaAdapter;
import com.example.projetoestagioaiko.api.OlhoVivoApiService;
import com.example.projetoestagioaiko.api.RetrofitClient;
import com.example.projetoestagioaiko.models.PrevisaoChegada;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

public class PrevisaoChegadaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PrevisaoChegadaAdapter adapter;
    private OlhoVivoApiService olhoVivoApiService;
    private List<PrevisaoChegada> listaPrevisoes;
    private String paradaId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_previsao_chegada));


        recyclerView = findViewById(R.id.recycler_view_previsao);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        olhoVivoApiService = RetrofitClient.getClient("https://aiko-olhovivo-proxy.aikodigital.io/").create(OlhoVivoApiService.class);

        listaPrevisoes = new ArrayList<>();
        adapter = new PrevisaoChegadaAdapter(listaPrevisoes);
        recyclerView.setAdapter(adapter);

        paradaId = getIntent().getStringExtra("PARADA_ID");
        carregarPrevisaoChegada(paradaId);

    }
    private void carregarPrevisaoChegada(String paradaId) {
        olhoVivoApiService.obterPrevisaoChegada(paradaId).enqueue(new Callback<List<PrevisaoChegada>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<PrevisaoChegada>> call, @NonNull Response<List<PrevisaoChegada>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    listaPrevisoes.clear();
                    listaPrevisoes.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PrevisaoChegada>> call, Throwable t) {

            }
        });

    }
}
