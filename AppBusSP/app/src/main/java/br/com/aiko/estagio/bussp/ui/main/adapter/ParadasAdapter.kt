package br.com.aiko.estagio.bussp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import br.com.aiko.estagio.bussp.databinding.ItemParadaBinding

class ParadasAdapter :
    ListAdapter<Parada, ParadasAdapter.ParadasViewHolder>(ParadasDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParadasViewHolder {
        val binding = ItemParadaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParadasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParadasViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ParadasViewHolder(
        private val binding: ItemParadaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(parada: Parada) {
            binding.tvCodigoParada.text = parada.cp.toString()
            binding.tvNomeParada.text = parada.np
            binding.tvEnderecoParada.text = parada.ed.replace("/ ", "\n")
        }
    }
}

private class ParadasDiffCallBack : DiffUtil.ItemCallback<Parada>() {
    override fun areItemsTheSame(oldItem: Parada, newItem: Parada) = oldItem.cp == newItem.cp

    override fun areContentsTheSame(oldItem: Parada, newItem: Parada) = oldItem == newItem

}