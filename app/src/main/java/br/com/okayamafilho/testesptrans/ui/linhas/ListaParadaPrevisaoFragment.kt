package br.com.okayamafilho.testesptrans.ui.linhas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.okayamafilho.testesptrans.data.RetrofitClient.spTransAPI
import br.com.okayamafilho.testesptrans.databinding.FragmentListaParadaPrevisaoBinding
import br.com.okayamafilho.testesptrans.domain.StopForecastCarLocation
import br.com.okayamafilho.testesptrans.domain.StopForecastLineBus
import br.com.okayamafilho.testesptrans.ui.adapters.BuslineStopForecastAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaParadaPrevisaoFragment : Fragment() {

    private lateinit var binding: FragmentListaParadaPrevisaoBinding
    private lateinit var buslineStopForecastAdapter: BuslineStopForecastAdapter
    private var job: Job? = null
    private var codigo: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaParadaPrevisaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        codigo = arguments?.getInt("codigo")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBuslineStopForecast.layoutManager = LinearLayoutManager(context)
        buslineStopForecastAdapter = BuslineStopForecastAdapter()

        binding.rvBuslineStopForecast.adapter = buslineStopForecastAdapter
        getLineBusStopForecast(codigo!!)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getLineBusStopForecast(codigo: Int) {
        job = CoroutineScope(Dispatchers.IO).launch {

            try {
                val authResponse =
                    spTransAPI.authResponse("")

                val headers = authResponse.headers().values("Set-Cookie")
                val cookie = headers.joinToString("; ")
                val searchStopForecastLineBusResponse : StopForecastLineBus  =
                    spTransAPI.searchStopForecastLineBus(cookie = cookie, codigo)

                withContext(Dispatchers.Main) {
                    if (searchStopForecastLineBusResponse != null) {
                        val listLineBusStopForecast = mutableListOf<StopForecastCarLocation>()
                        searchStopForecastLineBusResponse.p.l.forEach { lineBusStopResponseForecast ->
                            lineBusStopResponseForecast.vs.forEach { stopForecastCarLocations ->
                                listLineBusStopForecast.add(stopForecastCarLocations)
                            }
                        }
                        buslineStopForecastAdapter.adicionarLista(listLineBusStopForecast)
                    } else {
                        Toast.makeText(
                            context,
                            "Não foram encontradas linhas para esta pesquisa.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
    }
}