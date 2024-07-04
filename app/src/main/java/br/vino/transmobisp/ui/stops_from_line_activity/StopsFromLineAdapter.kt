package br.vino.transmobisp.ui.stops_from_line_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.vino.transmobisp.R
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.model.stops_from_line.StopWithVehicles

class StopsFromLineAdapter(private var stops : List<StopWithVehicles>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<StopsFromLineAdapter.ViewHolder>(){

    private var filteredList: List<StopWithVehicles> = stops

    interface OnItemClickListener {
        fun onItemClick(stopWithVehicles: StopWithVehicles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stop_from_line_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val line = filteredList[position]
        holder.bind(line, itemClickListener)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun updateStopsFromline(stopsFromLineList: List<StopWithVehicles>){
        stops = stopsFromLineList
        filteredList = stopsFromLineList
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        if (query.isEmpty()) {
            filteredList = stops
        } else {
            filteredList = stops.filter {
                it.cp.toString().contains(query, ignoreCase = true) ||
                it.np.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    // ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stopCode: TextView = itemView.findViewById(R.id.stop_code)
        val stopName: TextView = itemView.findViewById(R.id.stop_name)

        fun bind(stopWithVehicles: StopWithVehicles, clickListener: OnItemClickListener) {
            stopCode.text = stopWithVehicles.cp.toString()
            val stopNameFormatted = if (stopWithVehicles.np.isEmpty()) {"SEM IDENTIFICAÇÃO"}else{stopWithVehicles.np}
            stopName.text = stopNameFormatted

            itemView.setOnClickListener {
                clickListener.onItemClick(stopWithVehicles)
            }
        }

    }
}