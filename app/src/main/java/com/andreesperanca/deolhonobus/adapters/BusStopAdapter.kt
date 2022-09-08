package com.andreesperanca.deolhonobus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.adapters.BusStopAdapter.BusStopViewHolder
import com.andreesperanca.deolhonobus.databinding.RvBusStopItemBinding
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.ui.fragments.BusDetailsFragmentDirections
import com.andreesperanca.deolhonobus.ui.fragments.SearchFragmentDirections

class BusStopAdapter : RecyclerView.Adapter<BusStopViewHolder>() {

    private var busStopList: List<BusStop> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvBusStopItemBinding.inflate(inflater, parent, false)
        return BusStopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(busStopList[position])
    }

    override fun getItemCount(): Int = busStopList.size


    fun updateList(data: List<BusStop>?) {
        data?.let {
            busStopList = data
        }
        notifyItemChanged(busStopList.size)
    }

    inner class BusStopViewHolder(private val binding: RvBusStopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(busStop: BusStop) {

            binding.tvBusStopName.text =
                binding.root.context.getString(R.string.busStopName, busStop.name)
            binding.tvAddress.text =
                binding.root.context.getString(R.string.address, busStop.address)

            binding.tvDetails.setOnClickListener {
                if (it.findNavController().currentDestination?.id == R.id.searchFragment) {
                    it.findNavController()
                        .navigate(SearchFragmentDirections.actionSearchFragmentToBusStopDetailsFragment(busStop))
                } else {
                    it.findNavController().navigate(BusDetailsFragmentDirections.actionBusDetailsFragmentToBusStopDetailsFragment(busStop))
                }
            }
        }
    }
}