package br.com.daniel.aikoandroidestagio.ui.main.paradas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.databinding.FragmentParadasBinding
import br.com.daniel.aikoandroidestagio.enums.From
import br.com.daniel.aikoandroidestagio.model.Parada
import br.com.daniel.aikoandroidestagio.services.ApiService
import br.com.daniel.aikoandroidestagio.ui.maps.MapsActivity
import br.com.daniel.aikoandroidestagio.util.Constants
import kotlinx.coroutines.*
import java.io.Serializable

class ParadasFragment : Fragment() {

    private var _binding: FragmentParadasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParadasBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.buttonEncontrar.setOnClickListener {
            val nomeRua = binding.textoEnderecoPonto.editText?.text.toString()

            lifecycleScope.launch {
                val resposta = ApiService.getParadas(nomeRua)

                if (resposta.isSuccessful) {
                    val paradas = resposta.body()
                    if (paradas.isNullOrEmpty()) {
                        Toast.makeText(activity?.applicationContext, getString(R.string.nao_encontrei_pontos), Toast.LENGTH_LONG).show()

                    } else {
                        withContext(Dispatchers.Main) {
                            iniciaMapaActivity(paradas)
                        }
                    }
                } else {
                    Toast.makeText(activity?.applicationContext, getString(R.string.algo_errado), Toast.LENGTH_LONG).show()
                }
            }
        }

        return root
    }

    private fun iniciaMapaActivity(paradas: List<Parada>?) {
        val intent = Intent(activity, MapsActivity::class.java).apply {
            putExtra(Constants.from, From.PARADAS)
            putExtra(Constants.parada, paradas as Serializable)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}