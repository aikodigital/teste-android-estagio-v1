package br.com.aiko.estagio.bussp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import br.com.aiko.estagio.bussp.databinding.ItemParadaBinding
import br.com.aiko.estagio.bussp.ui.main.activity.InforOnibusActivity
import br.com.aiko.estagio.bussp.ui.main.activity.ParadasActivity

class ParadasAdapter(private val context: Context) :
    ListAdapter<Parada, ParadasAdapter.ParadasViewHolder>(ParadasDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParadasViewHolder {
        val binding = ItemParadaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParadasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParadasViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            val intent = Intent(context, InforOnibusActivity::class.java)
            intent.putExtra("parada", getItem(position).cp)
            context.startActivity(intent)
        }
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