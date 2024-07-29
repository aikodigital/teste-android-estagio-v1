package com.example.projetoestagioaiko.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoestagioaiko.api.OlhoVivoApiService;
import com.example.projetoestagioaiko.R;
import com.example.projetoestagioaiko.api.RetrofitClient;
import com.example.projetoestagioaiko.adapters.LinhaOnibusAdapter;
import com.example.projetoestagioaiko.models.LinhaOnibus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LinhasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinhaOnibusAdapter adapter;
    private OlhoVivoApiService olhoVivoApiService;
    private List<LinhaOnibus> listaLinhas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linhas);

        recyclerView = findViewById(R.id.recycler_view_linhas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        olhoVivoApiService = RetrofitClient.getClient("https://api.olhovivo.sptrans.com.br/").create(OlhoVivoApiService.class);
        listaLinhas = new ArrayList<>();
        adapter = new LinhaOnibusAdapter(listaLinhas);
        recyclerView.setAdapter(adapter);

        carregarLinhas();
    }

    private void carregarLinhas(){
        olhoVivoApiService.obterLinhas().enqueue(new Callback<List<LinhaOnibus>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<LinhaOnibus>> call, Response<List<LinhaOnibus>> response) {
                if(response.isSuccessful() && response.body() != null){
                    listaLinhas.clear();
                    listaLinhas.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<LinhaOnibus>> call, Throwable t) {

            }
        });
    }
}
