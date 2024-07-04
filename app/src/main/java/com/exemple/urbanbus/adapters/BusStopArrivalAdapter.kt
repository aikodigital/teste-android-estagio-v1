package com.exemple.urbanbus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exemple.urbanbus.R
import com.exemple.urbanbus.data.models.BusStopLineArrival
import com.exemple.urbanbus.databinding.ItemStopArrivalBinding
import com.exemple.urbanbus.utils.calculateMinutesDifference

class BusStopArrivalAdapter :
    RecyclerView.Adapter<BusStopArrivalAdapter.BusStopArrivalViewHolder>() {
        private val busStopArrivalList = mutableListOf<BusStopLineArrival>()

        fun setBusStopArrivalList(busStopArrivals: List<BusStopLineArrival>) {
            this.busStopArrivalList.clear()
            this.busStopArrivalList.addAll(busStopArrivals)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopArrivalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStopArrivalBinding.inflate(inflater, parent, false)
        return BusStopArrivalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusStopArrivalViewHolder, position: Int) {
        val busStopArrival = busStopArrivalList[position]
        holder.bind(busStopArrival)
    }

    override fun getItemCount(): Int {
        return busStopArrivalList.size
    }

    inner class BusStopArrivalViewHolder(
        private val binding: ItemStopArrivalBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stopArrival: BusStopLineArrival) {
            binding.lineName.text = stopArrival.mainTerminal
            binding.lineDirection.text = stopArrival.destination
            val arrivalDiff = calculateMinutesDifference(
                stopArrival.currentHour!!,
                stopArrival.vehicleArrivals[0].time
            )

            binding.forecastValue.text = if (arrivalDiff == -1) {
                stopArrival.vehicleArrivals.getOrNull(0)?.time ?: ""
            } else {
                arrivalDiff.toString()
            }

            binding.forecastUnit.visibility = if (arrivalDiff == -1) View.GONE else View.VISIBLE

            val nextArrival = stopArrival.vehicleArrivals.drop(1).joinToString(", ") { it.time }
            if (nextArrival != "") binding.nextForecastValue.text = nextArrival
        }
    }
}