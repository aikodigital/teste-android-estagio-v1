package com.example.projetoestagioaiko.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projetoestagioaiko.R;
import com.example.projetoestagioaiko.models.PrevisaoChegada;

import java.util.List;

public class PrevisaoChegadaAdapter extends RecyclerView.Adapter<PrevisaoChegadaAdapter.ViewHolder>  {
    private List<PrevisaoChegada> previsoes;

    public PrevisaoChegadaAdapter(List<PrevisaoChegada> previsoes) {
        this.previsoes = previsoes;
    }


    @NonNull
    @Override
    public PrevisaoChegadaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_previsao_chegada, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrevisaoChegadaAdapter.ViewHolder holder, int position) {
        PrevisaoChegada previsao = previsoes.get(position);
        holder.linhaNome.setText(previsao.getLinha());
        holder.tempoChegada.setText(previsao.getPrevisao());
    }

    @Override
    public int getItemCount() {
        return previsoes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView linhaNome;
        public TextView tempoChegada;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linhaNome = itemView.findViewById(R.id.parada_nome);
            tempoChegada = itemView.findViewById(R.id.tempo_chegada);

        }
    }
}
