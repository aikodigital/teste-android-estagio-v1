package br.com.aiko.estagio.bussp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.aiko.estagio.bussp.data.remote.response.LinhasLocalizada
import br.com.aiko.estagio.bussp.data.remote.response.VeiculoLocalizado
import br.com.aiko.estagio.bussp.databinding.ItemOnibusBinding

class PrevisaoParadaAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<Pair<VeiculoLocalizado, LinhasLocalizada>, PrevisaoParadaAdapter.PrevisaoViewHolder>(
        PrevisaoDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevisaoViewHolder {
        val binding = ItemOnibusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrevisaoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClick(item.second.cl)
        }
    }

    inner class PrevisaoViewHolder(private val binding: ItemOnibusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pair: Pair<VeiculoLocalizado, LinhasLocalizada>) {
            val (veiculo, linha) = pair
            binding.tvLeiteiro.text = linha.c
            binding.tvTerminalDestino.text = linha.lt0
            binding.tvTerminalOrigem.text = linha.lt1

            binding.tvPrexifoOnibus.text = veiculo.p.toString()
            binding.tvPrevisaoChegada.text = veiculo.t
        }
    }
}

private class PrevisaoDiffCallBack :
    DiffUtil.ItemCallback<Pair<VeiculoLocalizado, LinhasLocalizada>>() {
    override fun areItemsTheSame(
        oldItem: Pair<VeiculoLocalizado, LinhasLocalizada>,
        newItem: Pair<VeiculoLocalizado, LinhasLocalizada>
    ): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(
        oldItem: Pair<VeiculoLocalizado, LinhasLocalizada>,
        newItem: Pair<VeiculoLocalizado, LinhasLocalizada>
    ): Boolean {
        return oldItem == newItem
    }
}