package com.example.projetoestagioaiko.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoestagioaiko.R;
import com.example.projetoestagioaiko.models.PontoDeParada;

public class ViewHolder extends RecyclerView.ViewHolder  {
    TextView nomePonto;

    public ViewHolder(View itemView) {
        super(itemView);
        nomePonto = itemView.findViewById(R.id.nome_ponto);
    }

    public void bind(PontoDeParada ponto) {
        nomePonto.setText(ponto.getNome());
    }
}

