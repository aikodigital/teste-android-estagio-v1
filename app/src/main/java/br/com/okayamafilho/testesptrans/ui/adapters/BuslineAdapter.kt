package br.com.okayamafilho.testesptrans.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.okayamafilho.testesptrans.R
import br.com.okayamafilho.testesptrans.databinding.CardItemLinebusBinding
import br.com.okayamafilho.testesptrans.domain.LineBus
import com.squareup.picasso.Picasso


class BuslineAdapter(
    private val onClick: (LineBus) -> Unit
) : RecyclerView.Adapter<BuslineAdapter.BuslineViewHolder>() {

    private var listaBusline = emptyList<LineBus>()

    fun adicionarLista(lista: List<LineBus>) {
        listaBusline = lista
        notifyDataSetChanged()
    }

    inner class BuslineViewHolder(
        val binding: CardItemLinebusBinding
    ) : ViewHolder(binding.root) {
        fun bind(lineBus: LineBus) {

            Picasso.get()
                .load(R.drawable.img_dot)
                .into(binding.imageView)

            binding.txtCllinebus.text = lineBus.cl.toString()
            binding.txtLtlinebus.text = lineBus.lt
            binding.txtTslinebus.text = lineBus.ts
            binding.txtTplinebus.text = lineBus.tp

            //Evento de clique
            binding.cardItemBusLine.setOnClickListener {
                onClick(lineBus)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuslineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = CardItemLinebusBinding.inflate(
            layoutInflater, parent, false

        )
        return BuslineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BuslineViewHolder, position: Int) {
        val listBusLine = listaBusline[position]
        holder.bind(listBusLine)
    }

    override fun getItemCount(): Int {
        return listaBusline.size
    }
}