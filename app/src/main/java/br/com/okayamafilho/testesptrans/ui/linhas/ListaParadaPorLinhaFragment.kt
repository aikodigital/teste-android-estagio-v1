package br.com.okayamafilho.testesptrans.ui.linhas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.okayamafilho.testesptrans.R
import br.com.okayamafilho.testesptrans.data.RetrofitClient.spTransAPI
import br.com.okayamafilho.testesptrans.databinding.FragmentListaParadaBinding
import br.com.okayamafilho.testesptrans.domain.SearchStopsByLine
import br.com.okayamafilho.testesptrans.ui.adapters.BuslineStopAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaParadaPorLinhaFragment : Fragment() {

    private lateinit var binding: FragmentListaParadaBinding
    private lateinit var buslineStopAdapter: BuslineStopAdapter
    private var job: Job? = null
    private var codigo: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaParadaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        codigo = arguments?.getInt("codigo")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvBuslineStop.layoutManager = LinearLayoutManager(context)
        buslineStopAdapter = BuslineStopAdapter { lineBusStop ->
            val bundle = bundleOf("codigo" to lineBusStop.cp)
            view?.findNavController()?.navigate(R.id.action_ListaParadaFragment_to_listaParadaPrevisaoFragment, bundle)
        }

        binding.rvBuslineStop.adapter = buslineStopAdapter
        getLineBusStop(codigo!!)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getLineBusStop(codigo: Int) {
        var searchStopByLineResponse: List<SearchStopsByLine>? = null

        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResponse =
                    spTransAPI.authResponse("")

                val headers = authResponse.headers().values("Set-Cookie")
                val cookie = headers.joinToString("; ")

                searchStopByLineResponse = spTransAPI.searchStopBusLine(cookie = cookie, codigo)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            withContext(Dispatchers.Main) {
                if (searchStopByLineResponse!!.isNotEmpty()) {
                    val listLineBusStop = mutableListOf<SearchStopsByLine>()
                    searchStopByLineResponse!!.forEach { lineBusStopResponse ->
                        listLineBusStop.add(lineBusStopResponse)
                    }
                    buslineStopAdapter.adicionarLista(listLineBusStop)
                } else {
                    Toast.makeText(context, "Não foram encontradas linhas para esta pesquisa.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
    }
}