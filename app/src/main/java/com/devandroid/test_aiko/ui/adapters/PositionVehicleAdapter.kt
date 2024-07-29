package com.devandroid.test_aiko.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.ui.adapters.ArrivalForecastAdapter.ArrivalForecastViewHolder
import com.devandroid.test_aiko.models.Vehicle
import com.devandroid.test_aiko.models.VehicleForecast

class PositionVehicleAdapter(private var vehicles : List<Vehicle>) : RecyclerView.Adapter<PositionVehicleAdapter.PositionVehicleViewHolder>() {

    class PositionVehicleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vehicleName: TextView = view.findViewById(R.id.textViewVehiclePrefix)
        val arrivalTime: TextView = view.findViewById(R.id.textViewVehicleDetails)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PositionVehicleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_position_vehicle, parent, false)
        return PositionVehicleViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PositionVehicleViewHolder,
        position: Int
    ) {
        val vehicle = vehicles[position]
        holder.vehicleName.text = "Número do ônibus: ${vehicle.p.toString()}"
        holder.arrivalTime.text = "Horário do último Registro : ${vehicle.ta.substring(11,19)}"
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }


    fun updateVehicles(newVehicles: List<Vehicle>) {
        vehicles = newVehicles
        notifyDataSetChanged()
    }
}