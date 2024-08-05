package com.android.desafioaiko.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.desafioaiko.BusSchedule;
import com.android.desafioaiko.R;
import com.android.desafioaiko.model.Schedule;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConsultAdapter extends ArrayAdapter<Schedule> {

    public ConsultAdapter(@NonNull Context context, List<Schedule> resource) {
        super(context, 0, resource);
    }

    @NotNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.consult_item, parent, false);
        }

        TextView txtLetter = view.findViewById(R.id.txtLetter);
        TextView txtBusName = view.findViewById(R.id.txtConsultName);
        Schedule schedule = getItem(position);
        GridView busScheduleList = view.findViewById(R.id.listSchedules);

        txtLetter.setText(schedule.getC());
        txtBusName.setText(schedule.getLt0() + "/" + schedule.getLt1());
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(ConsultAdapter.this.getContext(), schedule.getVs());
        busScheduleList.setAdapter(scheduleAdapter);
        return view;
    }
}
