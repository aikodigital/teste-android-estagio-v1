package br.com.daniel.aikoandroidestagio.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.model.LX
import br.com.daniel.aikoandroidestagio.model.P
import br.com.daniel.aikoandroidestagio.ui.PrevisaoChegadaOnibus
import br.com.daniel.aikoandroidestagio.util.Constants

class PrevisaoChegadaLinhasAdapter(
    private val paradaPrevisao: P
) : RecyclerView.Adapter<PrevisaoChegadaLinhasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_linha_parada, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val linha = paradaPrevisao.l[position]
        holder.vincula(linha)
    }

    override fun getItemCount(): Int = paradaPrevisao.l.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun vincula(linha: LX) {

            val sentido =
                if (linha.sl == 1) itemView.context.getString(R.string.sentido_principal)
                else itemView.context.getString(R.string.sentido_secundario)

            itemView.findViewById<TextView>(R.id.sentido).text = sentido
            itemView.findViewById<TextView>(R.id.numero_letreiro).text = linha.c
            itemView.findViewById<TextView>(R.id.prefix_veiculo).text = linha.lt1
            itemView.findViewById<TextView>(R.id.nome_destino).text = linha.lt0

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, PrevisaoChegadaOnibus::class.java).apply {
                    putExtra(Constants.parada, linha)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}