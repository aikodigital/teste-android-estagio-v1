package com.andreesperanca.deolhonobus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.adapters.BusLineAdapter.BusLineViewHolder
import com.andreesperanca.deolhonobus.databinding.RvBusItemBinding
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.ui.fragments.BusStopDetailsFragmentDirections
import com.andreesperanca.deolhonobus.ui.fragments.SearchFragmentDirections

class BusLineAdapter() : RecyclerView.Adapter<BusLineViewHolder>() {

    private var busLineList: List<BusLine> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusLineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvBusItemBinding.inflate(inflater, parent, false)
        return BusLineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusLineViewHolder, position: Int) {
        holder.bind(busLineList[position])
    }

    override fun getItemCount(): Int = busLineList.size

    fun updateList(data: List<BusLine>?) {
        data?.let {
            busLineList = data
        }
        notifyItemChanged(busLineList.size)
    }

    inner class BusLineViewHolder(private val binding: RvBusItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(busLine: BusLine) {
            if (busLine.direction == 1) {
                binding.tvOrigin.text =
                    binding.root.context.getString(R.string.origin, busLine.mainTerminal)
                binding.tvDestination.text =
                    binding.root.context.getString(R.string.destination, busLine.secondaryTerminal)
            } else {
                binding.tvOrigin.text =
                    binding.root.context.getString(R.string.origin, busLine.secondaryTerminal)
                binding.tvDestination.text =
                    binding.root.context.getString(R.string.destination, busLine.mainTerminal)
            }
            binding.tvNumberLine.text =
                binding.root.context.getString(R.string.busNumber, busLine.firstLabel)

            binding.tvDetails.setOnClickListener {
                if (it.findNavController().currentDestination?.id == R.id.searchFragment) {
                    it.findNavController()
                        .navigate(
                            SearchFragmentDirections
                                .actionSearchFragmentToBusDetailsFragment(busLine)
                        )
                } else {
                    it.findNavController().navigate(
                        BusStopDetailsFragmentDirections.actionBusStopDetailsFragmentToBusDetailsFragment(
                            busLine
                        )
                    )
                }
            }
        }
    }
}