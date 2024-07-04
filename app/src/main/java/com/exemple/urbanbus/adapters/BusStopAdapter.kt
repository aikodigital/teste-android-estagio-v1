package com.exemple.urbanbus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exemple.urbanbus.data.models.BusStop
import com.exemple.urbanbus.databinding.ItemSimpleStopBinding

class BusStopAdapter(
    private val onStopClicked: (stop: BusStop) -> Unit
) : RecyclerView.Adapter<BusStopAdapter.StopSimpleViewHolder>() {
    private var stops = mutableListOf<BusStop>()

    fun setStopList(stopsList: List<BusStop>) {
        this.stops = stopsList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopSimpleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ItemSimpleStopBinding.inflate(inflater, parent, false)
        return StopSimpleViewHolder(binding, onStopClicked)
    }

    override fun onBindViewHolder(holder: StopSimpleViewHolder, position: Int) {
        val busStop = stops[position]
        holder.bind(busStop)
    }

    override fun getItemCount(): Int {
        return stops.size
    }

    inner class StopSimpleViewHolder(
        private val binding: ItemSimpleStopBinding,
        onStopClicked: (stop: BusStop) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(stop: BusStop) {
            val titleToShow = stop.name.ifBlank {
                stop.code.toString()
            }
            binding.name.text = titleToShow
            binding.location.text = stop.address

            binding.card.setOnClickListener {
                onStopClicked(stop)
            }
        }
    }
}

