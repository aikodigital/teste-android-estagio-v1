package com.andreesperanca.deolhonobus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.adapters.BusStopFavoriteAdapter.BusStopFavoriteViewHolder
import com.andreesperanca.deolhonobus.databinding.RvBusStopFavoriteItemBinding
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.ui.fragments.FavoriteFragment
import com.andreesperanca.deolhonobus.ui.fragments.FavoriteFragmentDirections

class BusStopFavoriteAdapter : RecyclerView.Adapter<BusStopFavoriteViewHolder>() {

    private var busStopList: List<BusStop> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopFavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvBusStopFavoriteItemBinding.inflate(inflater, parent, false)
        return BusStopFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusStopFavoriteViewHolder, position: Int) {
        holder.bind(busStopList[position])
    }

    override fun getItemCount(): Int = busStopList.size


    fun updateList(data: List<BusStop>?) {
        data?.let {
            busStopList = data
        }
        notifyItemChanged(busStopList.size)
    }

    inner class BusStopFavoriteViewHolder(private val binding: RvBusStopFavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(busStop: BusStop) {

            binding.tvBusStopName.text =
                binding.root.context.getString(R.string.busStopName, busStop.name)
            binding.tvAddress.text =
                binding.root.context.getString(R.string.address, busStop.address)

            binding.root.setOnClickListener {
                it.findNavController().
                navigate(FavoriteFragmentDirections.actionHomeFragmentToBusStopDetailsFragment(busStop))
            }
        }
    }
}