package br.com.samuel.testeaiko.ui.presentation.stops

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.samuel.testeaiko.core.domain.model.BusPosition
import br.com.samuel.testeaiko.databinding.HolderItemBusBinding

class BusAdapter(private val callback: Callback) : RecyclerView.Adapter<BusAdapter.Holder>() {

    private val items = mutableListOf<BusPosition>()

    fun addItems(items: List<BusPosition>) {
        this.items.addAll(items)
        notifyItemRangeInserted(0, items.size)
    }

    fun getItem(position: Int): BusPosition = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            HolderItemBusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]

        if (item.isAccessible) {
            holder.vb.icWheelchair.visibility = View.VISIBLE
        }

        holder.vb.tvPrefix.text = item.prefix.toString()

        holder.vb.tvArrivalForecast.visibility = View.GONE
        holder.vb.tvMessageArrivalForecast.visibility = View.GONE
    }

    interface Callback {
        fun onClick(position: Int)
    }

    inner class Holder(val vb: HolderItemBusBinding) : ViewHolder(vb.root) {

        init {
            vb.root.setOnClickListener { callback.onClick(adapterPosition) }
        }

    }

}