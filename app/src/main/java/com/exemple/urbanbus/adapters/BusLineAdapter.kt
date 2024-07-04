package com.exemple.urbanbus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exemple.urbanbus.data.models.BusLine
import com.exemple.urbanbus.databinding.ItemBusLineBinding

class BusLineAdapter (
    private val onLineClicked: (busLine: BusLine) -> Unit
) : RecyclerView.Adapter<BusLineAdapter.BusLineViewHolder>() {

    private val busLineList = mutableListOf<BusLine>()

    fun setBusLineList(busLineList: List<BusLine>) {
        this.busLineList.clear()
        this.busLineList.addAll(busLineList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusLineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBusLineBinding.inflate(inflater, parent, false)
        return BusLineViewHolder(binding, onLineClicked)
    }

    override fun onBindViewHolder(holder: BusLineViewHolder, position: Int) {
        val busLine = busLineList[position]
        holder.bind(busLine)
    }

    override fun getItemCount(): Int {
        return busLineList.size
    }

    inner class BusLineViewHolder(
        private val binding: ItemBusLineBinding,
        private val onLineClicked: (busLine: BusLine) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(busLine: BusLine) {
            binding.lineNumber.text = "${busLine.sign}-${busLine.signId}"

            if (busLine.direction == 1) {
                binding.startTerminal.text = busLine.mainTerminal
                binding.endTerminal.text = busLine.secondaryTerminal
            } else {
                binding.startTerminal.text = busLine.secondaryTerminal
                binding.endTerminal.text = busLine.mainTerminal
            }

            binding.root.setOnClickListener {
                onLineClicked(busLine)
            }
        }
    }
}