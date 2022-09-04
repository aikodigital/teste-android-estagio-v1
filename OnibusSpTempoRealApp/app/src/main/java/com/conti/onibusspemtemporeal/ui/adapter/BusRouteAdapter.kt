package com.conti.onibusspemtemporeal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.databinding.CardBusRouteBinding
import com.conti.onibusspemtemporeal.util.DiffUtilBusRoute

class BusRouteAdapter :
    ListAdapter<BusRoute, BusRouteAdapter.BusRouteViewModel>(DiffUtilBusRoute()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusRouteViewModel {
        val binding =
            CardBusRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BusRouteViewModel(binding)
    }

    override fun onBindViewHolder(holder: BusRouteViewModel, position: Int) {
        holder.bind(currentList[position])
    }

    private var onCardClickListener: ((BusRoute) -> Unit)? = null

    fun setonCardClickListener(listener: (BusRoute) -> Unit) {
        onCardClickListener = listener
    }

    private var onImageFavoriteClickListener: ((BusRoute) -> Unit)? = null

    fun setonImageFavoriteClickListener(listener: (BusRoute) -> Unit) {
        onImageFavoriteClickListener = listener
    }

    inner class BusRouteViewModel(private val binding: CardBusRouteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(busRoute: BusRoute) {
            binding.apply {
                if (busRoute.lineWay == 1) {
                    textViewDestiny.text =
                        busRoute.secondTerminal.lowercase().replaceFirstChar { it.uppercase() }
                    textViewOrigin.text =
                        busRoute.mainTerminal.lowercase().replaceFirstChar { it.uppercase() }
                } else if (busRoute.lineWay == 2) {
                    textViewDestiny.text =
                        busRoute.mainTerminal.lowercase().replaceFirstChar { it.uppercase() }
                    textViewOrigin.text =
                        busRoute.secondTerminal.lowercase().replaceFirstChar { it.uppercase() }

                }
                textViewLineCod.text = busRoute.lineCod.toString()

                cardBusRoute.setOnClickListener {
                    onCardClickListener?.let { it(busRoute) }
                }

                imageButtonFavoriteRoute.setOnClickListener {
                    onImageFavoriteClickListener?.let { it(busRoute) }
                }

            }
        }

    }
}