package com.example.projetoestagioaiko.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoestagioaiko.R;
import com.example.projetoestagioaiko.models.PontoDeParada;

import java.util.List;

public class PontoDeParadaAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<PontoDeParada> pontoDeParadaList;

    public PontoDeParadaAdapter(List<PontoDeParada> pontoDeParadaList) {
        this.pontoDeParadaList = pontoDeParadaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ponto_de_parada, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PontoDeParada ponto = pontoDeParadaList.get(position);
        holder.bind(ponto);
    }

    @Override
    public int getItemCount() {
        return pontoDeParadaList.size();
    }
}