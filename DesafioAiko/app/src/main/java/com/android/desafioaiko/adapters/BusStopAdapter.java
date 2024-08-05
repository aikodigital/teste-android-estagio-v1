package com.android.desafioaiko.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.desafioaiko.R;
import com.android.desafioaiko.model.BusStop;
import com.android.desafioaiko.model.LineInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BusStopAdapter extends ArrayAdapter<BusStop> {

    public BusStopAdapter(@NonNull Context context, List<BusStop> resource) {
        super(context, 0, resource);
    }

    @NotNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.bus_stop_item, parent, false);
        }
        BusStop stop = getItem(position);
        TextView txtStopName = view.findViewById(R.id.txtStopName);
        TextView txtAddress = view.findViewById(R.id.txtAddress);

        txtStopName.setText(stop.getNp());
        txtAddress.setText(stop.getEd());

        return view;
    }


}
