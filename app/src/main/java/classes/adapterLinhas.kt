package classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.apiolhovivo.MainActivity
import com.example.apiolhovivo.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class adapterLinhas(
    val linhas: List<Linha>

) : RecyclerView.Adapter<adapterLinhas.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.linhas_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = linhas.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = linhas[position]

        holder.nomeLinha.text = "${currentItem.lt}-${currentItem.tl}"
        holder.sentidoLinha.text = if (currentItem.sl == 1) "${currentItem.tp} ▶\n ${currentItem.ts}"
        else "${currentItem.ts} / ${currentItem.tp}"

        holder.btnMap.setOnClickListener{
            onClickListener?.onClick(position, currentItem)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeLinha: TextView = itemView.findViewById(R.id.txt_nomeOnibus)
        val sentidoLinha: TextView = itemView.findViewById(R.id.txt_horario)
        val btnMap: Button = itemView.findViewById(R.id.btn_toMap)
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Linha)
    }



}
