package com.andreesperanca.deolhonobus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.adapters.ForecastDialogAdapter.ForecastDialogViewHolder
import com.andreesperanca.deolhonobus.databinding.RvForecastBusItemBinding
import com.andreesperanca.deolhonobus.models.ListOfVehiclesLocated
import com.google.android.gms.maps.model.LatLng

class ForecastDialogAdapter(
    private val busStopList: List<ListOfVehiclesLocated>,
    private val seeBusLineInMap: (latLng: LatLng, hour: String) -> Unit
    ) : RecyclerView.Adapter<ForecastDialogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastDialogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvForecastBusItemBinding.inflate(inflater, parent, false)
        return ForecastDialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastDialogViewHolder, position: Int) {
        holder.bind(busStopList[position])
    }

    override fun getItemCount(): Int = busStopList.size

    inner class ForecastDialogViewHolder(private val binding: RvForecastBusItemBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(listOfVehiclesLocated: ListOfVehiclesLocated) {
            binding.tvArrivalForecast.text = binding.root.context.getString(R.string.arrivalForecast, listOfVehiclesLocated.arrivalForecast)
            if (listOfVehiclesLocated.accessibleForDisability) {
                binding.tvAccessibleForDisability.text = binding.root.context.getString(R.string.accessibleForDisability, "Sim")
            } else {
                binding.tvAccessibleForDisability.text = binding.root.context.getString(R.string.accessibleForDisability, "Não")
            }
            binding.tvDetails.setOnClickListener {
                seeBusLineInMap(LatLng(listOfVehiclesLocated.py,listOfVehiclesLocated.px),listOfVehiclesLocated.hourLastLocation)
            }
        }
    }
}