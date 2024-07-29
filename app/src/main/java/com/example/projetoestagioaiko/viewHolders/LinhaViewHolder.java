package com.example.projetoestagioaiko.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoestagioaiko.R;

public class LinhaViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCodigoLinha;
    public TextView tvNomeLinha;

    public LinhaViewHolder(@NonNull View itemView) {
        super(itemView);
            tvCodigoLinha = itemView.findViewById(R.id.tvCodigoLinha);
            tvNomeLinha = itemView.findViewById(R.id.tvNomeLinha);
    }
}
