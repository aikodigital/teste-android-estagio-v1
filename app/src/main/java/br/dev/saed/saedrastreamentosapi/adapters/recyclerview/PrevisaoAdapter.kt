package br.dev.saed.saedrastreamentosapi.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.dev.saed.saedrastreamentosapi.databinding.ItemPrevisaoBinding
import br.dev.saed.saedrastreamentosapi.models.VeiculosPrevisao

class PrevisaoAdapter() : Adapter<PrevisaoAdapter.PrevisaoViewHolder>() {

    private var veiculosPrevisaoList: List<VeiculosPrevisao> = mutableListOf()

    fun atualizar(veiculosPrevisaoList: List<VeiculosPrevisao>) {
        this.veiculosPrevisaoList = veiculosPrevisaoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevisaoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPrevisaoBinding.inflate(layoutInflater, parent, false)
        return PrevisaoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder, position: Int) {
        val veiculo = veiculosPrevisaoList[position]
        holder.bind(veiculo)
    }

    override fun getItemCount(): Int {
        return veiculosPrevisaoList.size
    }

    inner class PrevisaoViewHolder(
        private val binding: ItemPrevisaoBinding,
    ) : ViewHolder(
        binding.root
    ) {
        fun bind(parada: VeiculosPrevisao) {
            binding.textP.text = "P: ${parada.p}"
            binding.textT.text = "T: ${parada.t}"
            binding.textA.text = if (parada.a) "A: SIM" else "A: NÃO"
            binding.textTA.text = "TA: ${parada.ta}"
            binding.textPY.text = "PY: ${parada.py}"
            binding.textPX.text = "PX: ${parada.px}"
        }
    }

}