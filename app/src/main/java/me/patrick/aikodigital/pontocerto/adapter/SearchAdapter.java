package me.patrick.aikodigital.pontocerto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import me.patrick.aikodigital.pontocerto.R;
import me.patrick.aikodigital.pontocerto.model.BusStopPositionModel;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<BusStopPositionModel> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(BusStopPositionModel item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView busTitleView;
        public TextView busStreetView;

        public ViewHolder(View view, final OnItemClickListener listener, final List<BusStopPositionModel> items) {
            super(view);
            busTitleView = view.findViewById(R.id.busTitle);
            busStreetView = view.findViewById(R.id.busStreet);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(items.get(position));
                    }
                }
            });
        }
    }

    public SearchAdapter(List<BusStopPositionModel> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view, listener, items);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            holder.busTitleView.setText(items.get(position).getName());
            holder.busStreetView.setText(items.get(position).getAddress());
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(List<BusStopPositionModel> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }
}
