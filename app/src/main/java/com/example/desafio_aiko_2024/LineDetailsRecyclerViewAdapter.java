package com.example.desafio_aiko_2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

public class LineDetailsRecyclerViewAdapter extends RecyclerView.Adapter<LineDetailsRecyclerViewAdapter.MyViewHolder> {
    Context context;

    public LineDetailsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LineDetailsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_details_recycler_view_row, parent, false);

        return new LineDetailsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LineDetailsRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.busStopName.setText("AFONSO BRAZ B/C1");
        holder.busStopAddress.setText("R ARMINDA/ R BALTHAZAR DA VEIGA");
        holder.nextArrivalTime.setText("11:43");
        holder.arrivalTimes.setText("12:00");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView busStopName;
        MaterialTextView busStopAddress;
        MaterialTextView nextArrivalTime;
        MaterialTextView arrivalTimes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            busStopName = itemView.findViewById(R.id.bus_stop_name);
            busStopAddress = itemView.findViewById(R.id.bus_stop_address);
            nextArrivalTime = itemView.findViewById(R.id.next_arrival_time);
            arrivalTimes = itemView.findViewById(R.id.arrival_times);
        }
    }
}