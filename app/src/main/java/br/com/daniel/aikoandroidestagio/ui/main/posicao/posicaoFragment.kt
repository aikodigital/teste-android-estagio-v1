package br.com.daniel.aikoandroidestagio.ui.main.posicao

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.daniel.aikoandroidestagio.databinding.FragmentPosicaoBinding
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos
import br.com.daniel.aikoandroidestagio.services.ApiModule
import br.com.daniel.aikoandroidestagio.ui.ListaTodosVeiculos
import br.com.daniel.aikoandroidestagio.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class posicaoFragment : Fragment() {

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

        //Testando pegar todos os onibus de sp
        _binding?.buttonPosicao?.setOnClickListener {
            //Tentando pegar a resposta da API
            var resposta: Response<LocalizacaoVeiculos>
            lifecycleScope.launch {
                resposta = ApiModule.getPosicoes()

                var intent: Intent?
                if (resposta.isSuccessful) {

                    //Aqui vou guardar a posicao nos carros e o tamanho na classe
                    val localizacaoVeiculos = resposta.body()
                    localizacaoVeiculos?.carregarDados()

                    intent = Intent(activity, ListaTodosVeiculos::class.java).apply {
                        putExtra(Constants.veic, localizacaoVeiculos)
                    }
                } else {
                    intent = null
                    Log.d(TAG, "Erro resposta " + resposta.message())
                }
                Log.d(TAG, "Chegou no startactivity da lista, null? " + (intent == null).toString())

                withContext(Dispatchers.Main) {
                    intent?.let {
                        startActivity(intent)
                    }
                }
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}