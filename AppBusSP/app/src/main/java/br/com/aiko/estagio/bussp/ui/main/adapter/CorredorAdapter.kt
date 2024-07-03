package br.com.aiko.estagio.bussp.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.aiko.estagio.bussp.data.remote.response.Corredor
import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.databinding.ItemCorredorBinding
import br.com.aiko.estagio.bussp.databinding.ItemLinhaBinding

class CorredorAdapter :
    ListAdapter<Corredor, CorredorAdapter.CorredorViewHolder>(CorredorDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorredorViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemCorredorBinding.inflate(view, parent, false)
        return CorredorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CorredorViewHolder, position: Int) {
        val corredor = getItem(position)
        Log.d("CorredorAdapter", "Bind item: $corredor")
        holder.bind(corredor)
    }

    inner class CorredorViewHolder(private val binding: ItemCorredorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(corredor: Corredor) {
            binding.tvCodCorredor.text = corredor.cc.toString()
            binding.tvNomeCorredor.text = corredor.nc
        }
    }
}

private class CorredorDiffCallBack : DiffUtil.ItemCallback<Corredor>() {
    override fun areItemsTheSame(oldItem: Corredor, newItem: Corredor) = oldItem.cc == newItem.cc

    override fun areContentsTheSame(oldItem: Corredor, newItem: Corredor) = oldItem == newItem

}