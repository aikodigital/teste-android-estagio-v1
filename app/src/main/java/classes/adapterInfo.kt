package classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiolhovivo.R

class adapterInfo(
    val posicoes: List<VeiculoPrevisao>

) : RecyclerView.Adapter<adapterInfo.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterInfo.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.info_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapterInfo.MyViewHolder, position: Int) {
        var currentItem = posicoes[position]

        holder.txtNomeOnibus.text = currentItem.p.toString()
        holder.txtHorario.text = currentItem.t
    }

    override fun getItemCount() = posicoes.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNomeOnibus : TextView = itemView.findViewById(R.id.txt_nomeOnibus)
        val txtHorario : TextView = itemView.findViewById(R.id.txt_horario)
    }
}
