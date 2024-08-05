package com.android.desafioaiko.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.desafioaiko.model.BusScheduleModel;
import com.android.desafioaiko.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<BusScheduleModel> {

    public ScheduleAdapter(@NonNull Context context, List<BusScheduleModel> resource) {
        super(context, 0, resource);
    }

    @NotNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.schedule_item, parent, false);
        }

        BusScheduleModel busSchedule = getItem(position);

        TextView txtTime = view.findViewById(R.id.txtTime);

        txtTime.setText(String.valueOf(busSchedule.getT()));

        /* Obtem datetime de horario previsto e soma com o horario atual para verificar se
        * chega dentrode 5 minutos ou menos.
         */
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date scheduleTime = format.parse(busSchedule.getT());
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(scheduleTime);
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            int hourOfDayNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int minutesNow = Calendar.getInstance().get(Calendar.MINUTE);
            Log.d("HOUR OF DAY NOW", String.valueOf(hourOfDayNow));
            if(hourOfDay == hourOfDayNow){
                if((minutes - minutesNow) <= 5){
                    txtTime.setTextColor(Color.parseColor("#d6ca47"));
                }
            }
            else if((hourOfDay - hourOfDayNow) < 2){
                if(((minutes + 60) - (minutesNow)) <= 5) {
                    txtTime.setTextColor(Color.parseColor("#d6ca47"));
                }
            }
        }
        catch (Exception e){

        }
        return view;
    }


}
