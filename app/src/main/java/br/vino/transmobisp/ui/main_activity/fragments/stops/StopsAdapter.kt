package br.vino.transmobisp.ui.main_activity.fragments.stops

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.vino.transmobisp.R
import br.vino.transmobisp.model.Stop
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.model.stops_from_line.StopWithVehicles

class StopsAdapter(private var stops : List<Stop>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<StopsAdapter.ViewHolder>(){

    private var filteredList: List<Stop> = stops

    interface OnItemClickListener {
        fun onItemClick(stop: Stop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stops_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val line = filteredList[position]
        holder.bind(line, itemClickListener)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun updateStopsFromline(stopList: List<Stop>){
        stops = stopList
        filteredList = stopList
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
        val stopCode: TextView = itemView.findViewById(R.id.stops_list_stop_code)
        val stopName: TextView = itemView.findViewById(R.id.stops_list_stop_name)

        fun bind(stop: Stop, clickListener: OnItemClickListener) {
            stopCode.text = stop.cp.toString()
            val stopNameFormatted = if (stop.np.isEmpty()) {"SEM IDENTIFICAÇÃO"}else{stop.np}
            stopName.text = stopNameFormatted

            itemView.setOnClickListener {
                clickListener.onItemClick(stop)
            }
        }

    }
}