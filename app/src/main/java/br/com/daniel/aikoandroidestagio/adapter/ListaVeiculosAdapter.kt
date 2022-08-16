package br.com.daniel.aikoandroidestagio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.model.L
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos

class ListaVeiculosAdapter(
    private val listaLinhasVeiculos: LocalizacaoVeiculos
) : RecyclerView.Adapter<ListaVeiculosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_linhas_onibus, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val linhaEscolhida: L = listaLinhasVeiculos.l[position]

        linhaEscolhida.let {
            holder.vincula(it)
        }
    }

    override fun getItemCount(): Int {
        return listaLinhasVeiculos.l.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun vincula(linha: L) {
            val origem = itemView.findViewById<TextView>(R.id.titulo_origem)
            val destino = itemView.findViewById<TextView>(R.id.titulo_destino)
            val letreiro = itemView.findViewById<TextView>(R.id.titulo_secundario)
            val suporte = itemView.findViewById<TextView>(R.id.titulo_suporte)
            val quantidadeOnibus = itemView.findViewById<TextView>(R.id.quantidade_onibus)

            if (linha.sl == 1) {
//                Terminal principal
                origem.text = linha.lt1
                destino.text = linha.lt0
                letreiro.text = linha.c
                quantidadeOnibus.text = linha.vs.size.toString()
                val sentido = "Sentido: ${linha.lt0}"
                suporte.text = sentido
            } else {
//                Terminal secundario
                origem.text = linha.lt0
                destino.text = linha.lt1
                letreiro.text = linha.c
                quantidadeOnibus.text = linha.vs.size.toString()
                val sentido = "Sentido: ${linha.lt1}"
                suporte.text = sentido
            }
        }
    }
}