package com.andreesperanca.deolhonobus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.databinding.RvBusFavoriteItemBinding
import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.ui.fragments.FavoriteFragmentDirections


class BusLineFavoriteAdapter() : Adapter<BusLineFavoriteAdapter.BusLineFavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusLineFavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvBusFavoriteItemBinding.inflate(inflater, parent, false)
        return BusLineFavoriteViewHolder(binding)
    }

    private var busLineList: List<BusLine> = emptyList()

    override fun onBindViewHolder(holder: BusLineFavoriteViewHolder, position: Int) {
        holder.bind(busLineList[position])
    }

    override fun getItemCount(): Int = busLineList.size

    fun updateList(data: List<BusLine>?) {
        data?.let {
            busLineList = data
        }
        notifyItemChanged(busLineList.size)
    }

    inner class BusLineFavoriteViewHolder(private val binding: RvBusFavoriteItemBinding) :
        ViewHolder
            (binding.root) {

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

            binding.root.setOnClickListener {
                it.findNavController()
                    .navigate(FavoriteFragmentDirections.actionHomeFragmentToBusDetailsFragment(busLine))
            }
        }
    }
}