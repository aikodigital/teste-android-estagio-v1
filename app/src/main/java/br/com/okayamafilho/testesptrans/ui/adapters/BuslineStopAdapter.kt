package br.com.okayamafilho.testesptrans.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.okayamafilho.testesptrans.R
import br.com.okayamafilho.testesptrans.databinding.CardItemStopsbylineBinding
import br.com.okayamafilho.testesptrans.domain.SearchStopsByLine
import com.squareup.picasso.Picasso

class BuslineStopAdapter(
    private val onClick: (SearchStopsByLine) -> Unit
) : RecyclerView.Adapter<BuslineStopAdapter.BuslineStopViewHolder>() {

    private var listaBuslineStop = emptyList<SearchStopsByLine>()

    fun adicionarLista(lista: List<SearchStopsByLine>) {
        listaBuslineStop = lista
        notifyDataSetChanged()
    }

    inner class BuslineStopViewHolder(
        val binding: CardItemStopsbylineBinding
    ) : ViewHolder(binding.root) {
        fun bind(stopsByLine: SearchStopsByLine) {

            Picasso.get()
                .load(R.drawable.img_stop)
                .into(binding.imgStopBusline)

            binding.txtStopCp.text = stopsByLine.cp.toString()
            binding.txtStopNp.text = stopsByLine.np
            binding.txtStopEd.text = stopsByLine.ed

            //Evento de clique
            binding.cardItemStopsByLine.setOnClickListener {
                onClick(stopsByLine)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuslineStopViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = CardItemStopsbylineBinding.inflate(
            layoutInflater, parent, false

        )
        return BuslineStopViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BuslineStopViewHolder, position: Int) {
        val listBusLineStop = listaBuslineStop[position]
        holder.bind(listBusLineStop)
    }

    override fun getItemCount(): Int {
        return listaBuslineStop.size
    }
}