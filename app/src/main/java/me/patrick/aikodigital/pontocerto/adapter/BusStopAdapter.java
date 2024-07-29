package me.patrick.aikodigital.pontocerto.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.patrick.aikodigital.pontocerto.R;
import me.patrick.aikodigital.pontocerto.model.BusLineModel;
import me.patrick.aikodigital.pontocerto.model.EstimatedBusModel;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.ViewHolder> {

    private EstimatedBusModel response;
    private OnItemClickListener listener;
    private static boolean isExpanded = false;


    public interface OnItemClickListener {
        void onItemClick(int stopCode, int busPrefix);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stopTitleView;
        public TextView busPrefixView;
        public TextView busEstimatedTimeView;
        public View busAccordion;
        public TextView busNumber;
        public TextView busAccessible;
        public TextView busDestination;

        public ViewHolder(View view, final OnItemClickListener listener, final EstimatedBusModel item) {
            super(view);
            stopTitleView = view.findViewById(R.id.stopTitle);
            busPrefixView = view.findViewById(R.id.busPrefix);
            busEstimatedTimeView = view.findViewById(R.id.busEstimatedTime);
            busAccordion = view.findViewById(R.id.busAccordion);
            busNumber = view.findViewById(R.id.busNumber);
            busAccessible = view.findViewById(R.id.busAccessible);
            busDestination = view.findViewById(R.id.busDestination);




            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    isExpanded = !isExpanded;

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        if (position < item.getLines().size()) {
                            BusLineModel busLineModel = item.getLines().get(position);

                            if (busLineModel.getVehicles() != null && !busLineModel.getVehicles().isEmpty()) {
                                int busPrefix = busLineModel.getVehicles().get(0).getPrefix();
                                listener.onItemClick(busLineModel.getPrefix(), busPrefix);
                                if(isExpanded){
                                    showLayout(busAccordion);
                                }else{
                                    hideLayout(busAccordion);
                                }
                            }
                        }
                    }
                }
            });
        }
    }


    public BusStopAdapter(EstimatedBusModel response) {
        this.response = response;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop_view, parent, false);
        return new ViewHolder(view, listener, response);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (response == null) return;


        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            Context context = holder.itemView.getContext();
            holder.stopTitleView.setText(response.getName());
            holder.busPrefixView.setText(context.getString(R.string.bus_prefix_format, response.getLines().get(position).getLabel(), response.getLines().get(position).getOrigenLabel(),  response.getLines().get(position).getDestinationLabel()));

            holder.busEstimatedTimeView.setText(response.getLines().get(position).getVehicles().get(0).getEstimatedTime());
            holder.busNumber.setText(context.getString(R.string.bus_number, response.getLines().get(position).getVehicles().get(0).getPrefix()));
            holder.busAccessible.setText(context.getString(R.string.bus_accessible, response.getLines().get(position).getVehicles().get(0).isAccessible() ? "Sim" : "Não"));
            holder.busDestination.setText(context.getString(R.string.bus_destination, response.getLines().get(position).getDestinationLabel()));


        }
    }
    private static void showLayout(View layout) {
        if (layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
            layout.setTranslationY(layout.getHeight());
            ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationY", layout.getHeight(), 0);
            animator.setDuration(300);
            animator.start();
        }
    }

    private static void hideLayout(View layout) {
        if (layout.getVisibility() == View.VISIBLE) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationY",  0, layout.getHeight());
            animator.setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    layout.setVisibility(View.GONE);
                }
            });
            animator.start();
        }
    }

    @Override
    public int getItemCount() {
        return response.getLines().size();
    }

    public void updateList(EstimatedBusModel newResponse) {
        response = newResponse;
        notifyDataSetChanged();
    }
}
