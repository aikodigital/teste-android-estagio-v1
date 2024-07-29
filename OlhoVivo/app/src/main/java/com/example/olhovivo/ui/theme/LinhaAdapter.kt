package com.example.olhovivo.ui.theme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.olhovivo.R
import com.example.olhovivo.model.Linha

class LinhaAdapter(private val linhas: List<Linha>) : RecyclerView.Adapter<LinhaAdapter.LinhaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinhaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_linha, parent, false)
        return LinhaViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinhaViewHolder, position: Int) {
        holder.bind(linhas[position])
    }

    override fun getItemCount(): Int = linhas.size

    class LinhaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val linhaNome: TextView = itemView.findViewById(R.id.linha_nome)
        private val linhaDetalhes: TextView = itemView.findViewById(R.id.linha_detalhes)

        fun bind(linha: Linha) {
            linhaNome.text = "${linha.lt} - ${linha.ts} - ${linha.tp}"
            linhaDetalhes.text = if (linha.sl == 1) "Sentido Centro" else "Sentido Bairro"
        }
    }
}
