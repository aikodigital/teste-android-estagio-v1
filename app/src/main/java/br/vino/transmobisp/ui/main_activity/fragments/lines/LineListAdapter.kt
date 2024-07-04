package br.vino.transmobisp.ui.main_activity.fragments.lines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.vino.transmobisp.R
import br.vino.transmobisp.model.VehicleLine

class LineListAdapter(private var lines : List<VehicleLine>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<LineListAdapter.ViewHolder>(){

    private var filteredList: List<VehicleLine> = lines

    interface OnItemClickListener {
        fun onItemClick(vehicleLine: VehicleLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.line_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val line = filteredList[position]
        holder.bind(line, itemClickListener)
    }

    override fun getItemCount(): Int {
        return filteredList.distinctBy { it.c }.size
    }

    fun updateVehiclesLine(vehiclesLine: List<VehicleLine>){
        lines = vehiclesLine.distinctBy { it.c }
        filteredList = vehiclesLine.distinctBy { it.c }
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        if (query.isEmpty()) {
            filteredList = lines.distinctBy { it.c }
        } else {
            filteredList = lines.filter {
                it.c.contains(query, ignoreCase = true) ||
                it.lt0.contains(query, ignoreCase = true) ||
                it.lt1.contains(query, ignoreCase = true)
            }.distinctBy { it.c }
        }
        notifyDataSetChanged()
    }

    // ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sign: TextView = itemView.findViewById(R.id.sign)
        val origin_destination: TextView = itemView.findViewById(R.id.origin_destination)

        fun bind(vehicleLine: VehicleLine, clickListener: OnItemClickListener) {
            sign.text = vehicleLine.c
            val originDestinationText = "${vehicleLine.lt1} - ${vehicleLine.lt0}"
            origin_destination.text = originDestinationText

            itemView.setOnClickListener {
                clickListener.onItemClick(vehicleLine)
            }
        }

    }
}