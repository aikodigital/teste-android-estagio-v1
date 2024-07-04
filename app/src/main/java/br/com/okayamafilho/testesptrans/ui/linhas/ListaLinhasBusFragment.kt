package br.com.okayamafilho.testesptrans.ui.linhas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.okayamafilho.testesptrans.R
import br.com.okayamafilho.testesptrans.data.RetrofitClient.spTransAPI
import br.com.okayamafilho.testesptrans.databinding.FragmentListaLinhaBinding
import br.com.okayamafilho.testesptrans.domain.LineBus
import br.com.okayamafilho.testesptrans.ui.adapters.BuslineAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaLinhasBusFragment : Fragment() {

    private var _binding: FragmentListaLinhaBinding? = null
    private lateinit var buslineAdapter: BuslineAdapter
    private var job: Job? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListaLinhaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        buslineAdapter = BuslineAdapter { lineBus ->
            val bundle = bundleOf("codigo" to lineBus.cl)
            view?.findNavController()?.navigate(R.id.action_navigation_ListaLinhasBus_to_LinhaParadaFragment, bundle)
        }

        binding.rvBusline.adapter = buslineAdapter
        binding.rvBusline.layoutManager = LinearLayoutManager(context)

        return root
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getLineBus() {
        var searchStopResponse: List<LineBus>? = null

        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResponse =
                    spTransAPI.authResponse("7283c7558f5fee16beb01a03a6fb41d8b301461d7503d5744e483cbbeb3bb2f8")

                val headers = authResponse.headers().values("Set-Cookie")
                val cookie = headers.joinToString("; ")

                searchStopResponse = spTransAPI.searchLineBus(cookie = cookie, termosBusca = "8000")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            withContext(Dispatchers.Main) {
                if (searchStopResponse!!.isNotEmpty()) {
                    val listLineBus = mutableListOf<LineBus>()
                    searchStopResponse!!.forEach { lineBusResponse ->
                        listLineBus.add(lineBusResponse)
                    }
                    buslineAdapter.adicionarLista(listLineBus)
                } else {
                    Toast.makeText(context, "Não foram encontradas linhas para esta pesquisa.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job?.cancel()
    }

    override fun onStart() {
        super.onStart()
        getLineBus()
    }
}