package com.example.projetoestagioaiko.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoestagioaiko.R;
import com.example.projetoestagioaiko.models.LinhaOnibus;

import java.util.List;

public class LinhaOnibusAdapter extends RecyclerView.Adapter<LinhaOnibusAdapter.ViewHolder> {
        private List<LinhaOnibus> linhaList;

        public LinhaOnibusAdapter(List<LinhaOnibus> linhaList) {
            this.linhaList = linhaList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linha_onibus, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LinhaOnibus linha = linhaList.get(position);
            holder.bind(linha);
        }

        @Override
        public int getItemCount() {
            return linhaList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nomeLinha;

            public ViewHolder(View itemView) {
                super(itemView);
                nomeLinha = itemView.findViewById(R.id.nome_linha);
            }

            public void bind(LinhaOnibus linha) {
                nomeLinha.setText(linha.getNome());
            }
        }
    }