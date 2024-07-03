package com.example.desafio_aiko_2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

public class BusRecyclerViewAdapter extends RecyclerView.Adapter<BusRecyclerViewAdapter.MyViewHolder> {
    Context context;

    public BusRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BusRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new BusRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.lineNumber.setText("8000");
        holder.lineTerminals.setText("PCA.RAMOS DE AZEVEDO/TERMINAL LAPA");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView lineNumber;
        MaterialTextView lineTerminals;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lineNumber = itemView.findViewById(R.id.line_number);
            lineTerminals = itemView.findViewById(R.id.line_terminals);
        }
    }
}
