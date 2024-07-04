package com.example.desafio_aiko_2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

public class StopDetailsRecyclerViewAdapter extends RecyclerView.Adapter<StopDetailsRecyclerViewAdapter.MyViewHolder> {
    Context context;

    public StopDetailsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public StopDetailsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stop_details_recycler_view_row, parent, false);

        return new StopDetailsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StopDetailsRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.lineNumber.setText("8000");
        holder.lineTerminals.setText("PCA.RAMOS DE AZEVEDO/TERMINAL LAPA");
        holder.nextArrivalTime.setText("11:43");
        holder.arrivalTimes.setText("12:00");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView lineNumber;
        MaterialTextView lineTerminals;
        MaterialTextView nextArrivalTime;
        MaterialTextView arrivalTimes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lineNumber = itemView.findViewById(R.id.line_number);
            lineTerminals = itemView.findViewById(R.id.line_terminals);
            nextArrivalTime = itemView.findViewById(R.id.next_arrival_time);
            arrivalTimes = itemView.findViewById(R.id.arrival_times);
        }
    }
}