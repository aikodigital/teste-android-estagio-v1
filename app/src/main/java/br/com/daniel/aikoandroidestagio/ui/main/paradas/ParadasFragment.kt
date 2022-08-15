package br.com.daniel.aikoandroidestagio.ui.main.paradas

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.databinding.FragmentParadasBinding
import br.com.daniel.aikoandroidestagio.services.ApiModule
import br.com.daniel.aikoandroidestagio.ui.maps.MapsActivity
import br.com.daniel.aikoandroidestagio.util.Constants
import kotlinx.coroutines.*
import java.io.Serializable

class ParadasFragment : Fragment() {

    private var _binding: FragmentParadasBinding? = null
    private val TAG = "DEBUG"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val paradasViewModel =
            ViewModelProvider(this).get(ParadasViewModel::class.java)
        _binding = FragmentParadasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonEncontrar.setOnClickListener {
            val nomeRua = binding.textoEnderecoPonto.editText?.text.toString()

            lifecycleScope.launch {
                val resposta = ApiModule.getParadas(nomeRua)
                if (resposta.isSuccessful) {
                    val paradas = resposta.body()
                    if (paradas.isNullOrEmpty()) {
                        Toast.makeText(activity?.applicationContext, getString(R.string.nao_encontrei_pontos), Toast.LENGTH_LONG).show()
                    } else {
                        withContext(Dispatchers.Main) {
                            val intent = Intent(activity, MapsActivity::class.java).apply {
                                putExtra(Constants.from, 3)
                                putExtra(Constants.parada, paradas as Serializable)
                            }
                            startActivity(intent)
                        }
                    }
                } else {
                    Toast.makeText(activity?.applicationContext, getString(R.string.algo_errado), Toast.LENGTH_LONG).show()
                }
            }
            //todo: Consegui pegar o nome da rua assim, mover para onde permite pegar a localização
//            context?.let {
//                CoroutineScope(Dispatchers.IO).launch {
//                    val enderecos = Geocoder(it).getFromLocation(-23.5454758, -46.6455341,1)
//                    delay(500)
//                    if (enderecos != null && enderecos.size > 0) {
//                        val endereco: Address = enderecos.get(0)
//                        val addressLine = endereco.getAddressLine(0)
//                        Log.d(TAG, "addressLine: $addressLine")
//                    }
//                }
//            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}