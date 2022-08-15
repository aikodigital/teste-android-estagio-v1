package br.com.daniel.aikoandroidestagio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.model.VX

class PrevisaoChegadaOnibusAdapter(
    private val veiculos: List<VX>
) : RecyclerView.Adapter<PrevisaoChegadaOnibusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_previsao_onibus, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val veiculo = veiculos[position]
        holder.vincula(veiculo)
    }

    override fun getItemCount(): Int = veiculos.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun vincula(veiculo: VX) {
            itemView.findViewById<TextView>(R.id.prefix_veiculo).text = veiculo.p
            itemView.findViewById<TextView>(R.id.horario_previsto).text = veiculo.t
        }
    }
}