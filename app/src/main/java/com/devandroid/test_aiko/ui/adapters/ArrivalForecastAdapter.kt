package com.devandroid.test_aiko.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.models.ArrivalForecast
import com.devandroid.test_aiko.models.Vehicle
import com.devandroid.test_aiko.models.VehicleForecast

class ArrivalForecastAdapter(private var vehicles: List<VehicleForecast>) : RecyclerView.Adapter<ArrivalForecastAdapter.ArrivalForecastViewHolder>() {

    class ArrivalForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vehicleName: TextView = view.findViewById(R.id.textViewVehicleName)
        val arrivalTime: TextView = view.findViewById(R.id.textViewArrivelTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArrivalForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_arrival_forecast, parent, false)
        return ArrivalForecastViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ArrivalForecastViewHolder,
        position: Int
    ) {
        val vehicle = vehicles[position]
        holder.vehicleName.text = "Veículo: ${vehicle.p}"
        holder.arrivalTime.text = "Previsão De Chegada: ${vehicle.ta.substring(11,19)}"
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    fun updateVehicles(newVehicles: List<VehicleForecast>) {
        vehicles = newVehicles
        notifyDataSetChanged()
    }

    fun clear(){
        vehicles = emptyList()
        notifyDataSetChanged()
    }


}

