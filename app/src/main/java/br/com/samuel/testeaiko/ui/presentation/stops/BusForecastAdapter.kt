package br.com.samuel.testeaiko.ui.presentation.stops

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.samuel.testeaiko.R
import br.com.samuel.testeaiko.core.domain.model.BusStopForecast
import br.com.samuel.testeaiko.databinding.HolderItemBusBinding

class BusForecastAdapter(private val context: Context) :
    RecyclerView.Adapter<BusForecastAdapter.Holder>() {

    private val items = mutableListOf<BusStopForecast>()

    fun addItems(items: List<BusStopForecast>) {
        this.items.addAll(items)
        notifyItemRangeInserted(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            HolderItemBusBinding.inflate(LayoutInflater.from(context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val forecast = items[position]

        holder.vb.tvMessageArrivalForecast.visibility = View.VISIBLE
        holder.vb.tvArrivalForecast.visibility = View.VISIBLE

        if (forecast.isAccessible) {
            holder.vb.icWheelchair.visibility = View.VISIBLE
        }

        holder.vb.tvPrefix.text = forecast.prefix.toString()
        holder.vb.tvArrivalForecast.text = context.getString(R.string.text_arrival_forecast, forecast.timestamp.toString())
    }

    inner class Holder(val vb: HolderItemBusBinding) : ViewHolder(vb.root)

}