package br.com.aiko.estagio.bussp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.aiko.estagio.bussp.data.remote.response.Empresa
import br.com.aiko.estagio.bussp.data.remote.response.EmpresasAreaOcupcao
import br.com.aiko.estagio.bussp.databinding.ItemEmpresaBinding

class EmpresaAdapter :
    ListAdapter<Pair<Empresa, EmpresasAreaOcupcao>, EmpresaAdapter.EmpresaViewHolder>(
        EmpresaDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpresaViewHolder {
        val binding = ItemEmpresaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmpresaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EmpresaViewHolder(private val binding: ItemEmpresaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pair: Pair<Empresa, EmpresasAreaOcupcao>) {
            val (empresa, area) = pair
            binding.tvCodigoOperecao.text = area.a.toString()
            binding.tvCodigoEmpresa.text = "cod. ref: ${empresa.c}"
            binding.tvNomeEmpresa.text = empresa.n
        }
    }

}

private class EmpresaDiffCallBack : DiffUtil.ItemCallback<Pair<Empresa, EmpresasAreaOcupcao>>() {
    override fun areItemsTheSame(
        oldItem: Pair<Empresa, EmpresasAreaOcupcao>,
        newItem: Pair<Empresa, EmpresasAreaOcupcao>
    ): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(
        oldItem: Pair<Empresa, EmpresasAreaOcupcao>,
        newItem: Pair<Empresa, EmpresasAreaOcupcao>
    ): Boolean {
        return oldItem == newItem
    }

}