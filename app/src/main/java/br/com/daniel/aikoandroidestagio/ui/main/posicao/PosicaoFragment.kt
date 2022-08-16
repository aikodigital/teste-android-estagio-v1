package br.com.daniel.aikoandroidestagio.ui.main.posicao

import android.content.Intent
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
import br.com.daniel.aikoandroidestagio.databinding.FragmentPosicaoBinding
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos
import br.com.daniel.aikoandroidestagio.services.ApiModule
import br.com.daniel.aikoandroidestagio.ui.ListaTodosVeiculos
import br.com.daniel.aikoandroidestagio.ui.main.MainActivity
import br.com.daniel.aikoandroidestagio.ui.maps.MapsActivity
import br.com.daniel.aikoandroidestagio.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.Serializable

class PosicaoFragment : Fragment() {

    private var _binding: FragmentPosicaoBinding? = null
    private val TAG = "DEBUG"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val posicaoViewModel = ViewModelProvider(this).get(PosicaoViewModel::class.java)
        _binding = FragmentPosicaoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding?.buttonPosicao?.setOnClickListener {
            //Tentando pegar a resposta da API
            var resposta: Response<LocalizacaoVeiculos>
            lifecycleScope.launch {
                resposta = ApiModule.getPosicoes()

                var localizacaoVeiculos: LocalizacaoVeiculos? = null
                if (resposta.isSuccessful) {
                    //Aqui vou guardar a posicao nos carros e o tamanho na classe
                    localizacaoVeiculos = resposta.body()
                }

                localizacaoVeiculos?.let {
                    val intent = Intent(context, ListaTodosVeiculos::class.java).apply {
                        putExtra(Constants.veic, it)
                    }
                    startActivity(intent)
                } ?: listaNull()
            }
        }


        return root
    }

    private fun listaNull() {
        Toast.makeText(context,getString(R.string.algo_errado_veiculos), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}