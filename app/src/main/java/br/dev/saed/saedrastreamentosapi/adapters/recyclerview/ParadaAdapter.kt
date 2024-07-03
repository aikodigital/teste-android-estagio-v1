package br.dev.saed.saedrastreamentosapi.adapters.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.dev.saed.saedrastreamentosapi.databinding.ItemParadaBinding
import br.dev.saed.saedrastreamentosapi.models.Parada

class ParadaAdapter(
    private val cliqueOnibus: (Parada) -> Unit,
    private val cliqueMapa: (Parada) -> Unit
) : Adapter<ParadaAdapter.ParadaViewHolder>() {

    private var paradas: List<Parada> = mutableListOf()

    fun atualizar(paradas: List<Parada>) {
        this.paradas = paradas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParadaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemParadaBinding.inflate(layoutInflater, parent, false)
        return ParadaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParadaViewHolder, position: Int) {
        val parada = paradas[position]
        holder.bind(parada)
    }

    override fun getItemCount(): Int {
        return paradas.size
    }

    inner class ParadaViewHolder(
        private val binding: ItemParadaBinding,
    ) : ViewHolder(
        binding.root
    ) {
        fun bind(parada: Parada) {
            binding.textCP.text = "CP: ${parada.cp}"
            binding.textNP.text = if (parada.np.isNotBlank()) "NP: ${parada.np}" else "NP: S/N"
            binding.textED.text = "ED: ${parada.ed}"
            binding.buttonParadaOnibus.setOnClickListener {
                cliqueOnibus(parada)
            }
            binding.buttonParadaMapa.setOnClickListener {
                cliqueMapa(parada)
            }
        }
    }
}