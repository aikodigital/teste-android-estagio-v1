package br.vino.transmobisp.ui.stop_vehicles_forecast_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.vino.transmobisp.R
import br.vino.transmobisp.model.stops_from_line.VehicleStatus

class StopVehiclesForecastAdapter(private val vehicleStatusList : List<VehicleStatus>) : RecyclerView.Adapter<StopVehiclesForecastAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stop_vehicle_forecast_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val line = vehicleStatusList[position]
        holder.bind(line)
    }

    override fun getItemCount(): Int {
        return vehicleStatusList.size
    }

    // ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleId: TextView = itemView.findViewById(R.id.stop_vehicle_forecast_vehicle)
        val forecastTime: TextView = itemView.findViewById(R.id.stop_vehicle_forecast_forecast_time)

        fun bind(vehicleStatus: VehicleStatus) {
            vehicleId.text = vehicleStatus.p
            val forecastTimeFormatted = "Previsão de chegada - ${vehicleStatus.t}"
            forecastTime.text = forecastTimeFormatted

        }

    }
}