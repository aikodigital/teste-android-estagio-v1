package com.example.projetoestagioaiko.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoestagioaiko.R;

public class ParadaViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCodigoParada;
    public TextView tvNomeParada;

    public ParadaViewHolder(@NonNull View itemView) {
        super(itemView);
            tvCodigoParada = itemView.findViewById(R.id.tvCodigoParada);
            tvNomeParada = itemView.findViewById(R.id.tvNomeParada);
    }
}
