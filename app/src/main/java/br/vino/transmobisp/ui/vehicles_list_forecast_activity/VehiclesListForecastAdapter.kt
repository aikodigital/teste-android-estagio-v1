package br.vino.transmobisp.ui.vehicles_list_forecast_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.vino.transmobisp.R
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.model.stops_from_line.StopWithVehicles
import br.vino.transmobisp.model.stops_from_line.VehicleStatus
import br.vino.transmobisp.model.vehicles_lines_from_stop.LineWithVehicles

class VehiclesListForecastAdapter(private var lines : List<VehicleStatus>) : RecyclerView.Adapter<VehiclesListForecastAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicles_list_forecast_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val line = lines[position]
        holder.bind(line)
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    // ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleCode: TextView = itemView.findViewById(R.id.vehicle_list_forecast_vehicle_code)
        val vehicleForecastTime: TextView = itemView.findViewById(R.id.vehicle_list_forecast_forecast_time)

        fun bind(vehicleStatus: VehicleStatus) {
            vehicleCode.text = vehicleStatus.p
            val vehicleForecastTimeFormatted = "Previsão de chegada - ${vehicleStatus.t}"
            vehicleForecastTime.text = vehicleForecastTimeFormatted
        }

    }
}