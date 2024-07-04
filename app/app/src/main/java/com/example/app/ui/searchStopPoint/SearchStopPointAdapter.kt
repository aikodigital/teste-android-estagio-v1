package com.example.app.ui.searchStopPoint

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.model.BusWithTimeResponse
import com.example.app.databinding.LineItemBinding
import com.example.app.databinding.SearchBusItemBinding
import com.example.app.domain.model.Line

class SearchStopPointAdapter(
    private val bus: List<BusWithTimeResponse>
) : RecyclerView.Adapter<SearchStopPointAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SearchBusItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = bus.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bus = bus[position]

        holder.binding.apply {
            tvBusPrefix.text = bus.busPrefix.toString()
            tvIsAccessible.text = if (bus.isAccessible == true) "Sim" else "Não"
            tvArrivalForecast.text = "Previsão de chegada: ${bus.arrivalForecast}"
        }
    }

    inner class MyViewHolder(val binding: SearchBusItemBinding) : RecyclerView.ViewHolder(binding.root)
}