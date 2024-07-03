package br.dev.saed.saedrastreamentosapi.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.dev.saed.saedrastreamentosapi.databinding.ItemLinhaBinding
import br.dev.saed.saedrastreamentosapi.models.Linha

class LinhaAdapter() : Adapter<LinhaAdapter.LinhaViewHolder>() {

    private var linhas: List<Linha> = mutableListOf()

    fun atualizar(linhas: List<Linha>) {
        this.linhas = linhas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinhaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemLinhaBinding.inflate(layoutInflater, parent, false)
        return LinhaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LinhaViewHolder, position: Int) {
        val linha = linhas[position]
        holder.bind(linha)
    }

    override fun getItemCount(): Int {
        return linhas.size
    }

    inner class LinhaViewHolder(
        private val binding: ItemLinhaBinding,
    ) : ViewHolder(
        binding.root
    ) {
        fun bind(linha: Linha) {
            binding.textCL.text = "CL: ${linha.cl}"
            binding.textLC.text = if (linha.lc) "LC: SIM" else "LC: NÃO"
            binding.textLT.text = "LT: ${linha.lt}"
            binding.textSL.text = "SL: ${linha.sl}"
            binding.textTL.text = "TL: ${linha.tl}"
            binding.textTP.text = "TP: ${linha.tp}"
            binding.textTS.text = "TS: ${linha.ts}"
        }
    }

}