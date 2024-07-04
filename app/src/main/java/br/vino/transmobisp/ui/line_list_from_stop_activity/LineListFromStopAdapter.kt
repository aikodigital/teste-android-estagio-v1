package br.vino.transmobisp.ui.line_list_from_stop_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.vino.transmobisp.R
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.model.stops_from_line.StopWithVehicles
import br.vino.transmobisp.model.vehicles_lines_from_stop.LineWithVehicles

class LineListFromStopAdapter(private var lines : List<LineWithVehicles>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<LineListFromStopAdapter.ViewHolder>(){

    private var filteredList: List<LineWithVehicles> = lines

    interface OnItemClickListener {
        fun onItemClick(lineWithVehicles: LineWithVehicles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.line_list_from_stop_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val line = filteredList[position]
        holder.bind(line, itemClickListener)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun updateLines(lineWithVehiclesList: List<LineWithVehicles>){
        lines = lineWithVehiclesList
        filteredList = lineWithVehiclesList
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        if (query.isEmpty()) {
            filteredList = lines
        } else {
            filteredList = lines.filter {
                it.c.contains(query, ignoreCase = true) ||
                it.lt0.contains(query, ignoreCase = true) ||
                it.lt1.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    // ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lineCode: TextView = itemView.findViewById(R.id.line_list_from_stop_code)
        val lineOriginDestination: TextView = itemView.findViewById(R.id.line_list_from_stop_origin_destination)

        fun bind(lineWithVehicles: LineWithVehicles, clickListener: OnItemClickListener) {
            lineCode.text = lineWithVehicles.c
            val stopNameFormatted = "${lineWithVehicles.lt1} - ${lineWithVehicles.lt0}"
            lineOriginDestination.text = stopNameFormatted

            itemView.setOnClickListener {
                clickListener.onItemClick(lineWithVehicles)
            }
        }

    }
}