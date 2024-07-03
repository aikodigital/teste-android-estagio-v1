package br.dev.saed.saedrastreamentosapi.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.dev.saed.saedrastreamentosapi.databinding.ItemCorredorBinding
import br.dev.saed.saedrastreamentosapi.models.Corredor

class CorredorAdapter() : Adapter<CorredorAdapter.CorredorViewHolder>() {

    private var corredores: List<Corredor> = mutableListOf()

    fun atualizar(corredores: List<Corredor>) {
        this.corredores = corredores
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorredorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemCorredorBinding.inflate(layoutInflater, parent, false)
        return CorredorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CorredorViewHolder, position: Int) {
        val corredor = corredores[position]
        holder.bind(corredor)
    }

    override fun getItemCount(): Int {
        return corredores.size
    }

    inner class CorredorViewHolder(
        private val binding: ItemCorredorBinding,
    ) : ViewHolder(
        binding.root
    ) {
        fun bind(corredor: Corredor) {
            binding.textCC.text = "CC: ${corredor.cc}"
            binding.textNC.text = "NC: ${corredor.nc}"
        }
    }

}