package br.com.okayamafilho.testesptrans.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.okayamafilho.testesptrans.R
import br.com.okayamafilho.testesptrans.databinding.CardItemStopForecastBinding
import br.com.okayamafilho.testesptrans.domain.StopForecastCarLocation
import com.squareup.picasso.Picasso

class BuslineStopForecastAdapter(
) : RecyclerView.Adapter<BuslineStopForecastAdapter.BuslineStopViewHolder>() {

    private var listaBuslineStopForecast = emptyList<StopForecastCarLocation>()

    fun adicionarLista(lista: List<StopForecastCarLocation>) {
        listaBuslineStopForecast = lista
        notifyDataSetChanged()
    }

    inner class BuslineStopViewHolder(
        val binding: CardItemStopForecastBinding
    ) : ViewHolder(binding.root) {
        fun bind(stopForecastLineBus: StopForecastCarLocation) {

            Picasso.get()
                .load(R.drawable.img_stop_forecast)
                .into(binding.imgForecast)

            binding.txtForecastCp.text = stopForecastLineBus.p
            binding.txtForecastPrefixo.text = "Prefixo do ônibus: ${stopForecastLineBus.p}"
            binding.txtForecastHorario.text = "Previsão de chegada: ${stopForecastLineBus.t}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuslineStopViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = CardItemStopForecastBinding.inflate(
            layoutInflater, parent, false

        )
        return BuslineStopViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BuslineStopViewHolder, position: Int) {
        val listBusLineStop = listaBuslineStopForecast[position]
        holder.bind(listBusLineStop)
    }

    override fun getItemCount(): Int {
        return listaBuslineStopForecast.size
    }
}