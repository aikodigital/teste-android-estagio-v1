package br.com.samuel.testeaiko.ui.presentation.stops

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.samuel.testeaiko.core.domain.model.BusStop
import br.com.samuel.testeaiko.core.domain.model.BusStopForecast
import br.com.samuel.testeaiko.databinding.HolderItemStopBinding

class StopsAdapter(private val callback: Callback) : RecyclerView.Adapter<StopsAdapter.Holder>() {

    private val items = mutableListOf<BusStop>()

    fun addItems(items: List<BusStop>) {
        this.items.addAll(items)
        notifyItemRangeInserted(0, items.size)
    }

    fun getItem(position: Int) = items[position]

    fun addForecasts(forecasts: List<BusStopForecast>, position: Int) {
        val item = items[position]
        item.forecasts = forecasts
        items[position] = item
        notifyDataSetChanged() // notifyItemChanged() remove outros elementos da lista
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            HolderItemStopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, parent.context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val busStop = items[position]
        holder.vb.tvName.text = busStop.name
        holder.vb.tvAddress.text = busStop.address

        if (!busStop.forecasts.isNullOrEmpty()) {
            holder.vb.divider.visibility = View.VISIBLE
            holder.vb.rvBusForecast.visibility = View.VISIBLE
            holder.forecastAdapter.addItems(busStop.forecasts.orEmpty())
        }
    }

    interface Callback {
        fun onClick(position: Int)
    }

    inner class Holder(val vb: HolderItemStopBinding, context: Context) : ViewHolder(vb.root) {

        val forecastAdapter = BusForecastAdapter(context)

        init {
            vb.rvBusForecast.adapter = forecastAdapter
            vb.rvBusForecast.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            vb.rvBusForecast.setHasFixedSize(true)

            vb.root.setOnClickListener { callback.onClick(adapterPosition) }
        }
    }

}