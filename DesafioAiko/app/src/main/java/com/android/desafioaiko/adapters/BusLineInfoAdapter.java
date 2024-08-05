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
import com.android.desafioaiko.model.LineInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/* Os adaptadores tem a função de receber os dados de fonte externa
* e adiciona-los em cada celula.
*
 */
public class BusLineInfoAdapter extends ArrayAdapter<LineInfo> {

    public BusLineInfoAdapter(@NonNull Context context, List<LineInfo> resource) {
        super(context, 0, resource);
    }

    @NotNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.bus_line_info_item, parent, false);
        }

        LineInfo lineInfo = getItem(position);

        TextView txtInfoCode = view.findViewById(R.id.txtInfoCode);
        TextView txtInfoCiruclar = view.findViewById(R.id.txtInfoCircular);
        TextView txtInfoLetter = view.findViewById(R.id.txtInfoLetter);
        TextView txtOrigin = view.findViewById(R.id.txtOrigin);
        TextView txtDestinity = view.findViewById(R.id.txtDestinity);

        //Se for circular
        txtInfoCode.setText(String.valueOf(lineInfo.getCl()));
        if(lineInfo.isLc()){
            txtInfoCiruclar.setText("Sim");
        }
        else{
            txtInfoCiruclar.setText("Não");
        }

        txtInfoLetter.setText(lineInfo.getLt());
        txtOrigin.setText(lineInfo.getTp());
        txtDestinity.setText(lineInfo.getTs());


        return view;
    }

}
