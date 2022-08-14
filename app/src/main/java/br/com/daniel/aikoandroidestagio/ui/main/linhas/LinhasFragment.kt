package br.com.daniel.aikoandroidestagio.ui.main.linhas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.daniel.aikoandroidestagio.databinding.FragmentLinhasBinding
import br.com.daniel.aikoandroidestagio.services.ApiModule
import br.com.daniel.aikoandroidestagio.ui.maps.MapsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class LinhasFragment : Fragment() {

    private var _binding: FragmentLinhasBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val linhasViewModel =
            ViewModelProvider(this).get(LinhasViewModel::class.java)
        _binding = FragmentLinhasBinding.inflate(inflater, container, false)

        val valor = _binding?.textPosicaoLinha?.editText!!.text

        _binding?.buttonBuscarLinha?.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                configuraBuscaPorLinha(valor.toString())
            }
        }

        val root: View = binding.root
        return root
    }

    private suspend fun configuraBuscaPorLinha(valor: String) {

        //TODO: temporario ate resolver API
        val linhas = ApiModule.criaLinhasFake()
        val intent = Intent(activity, MapsActivity::class.java).apply {
            putExtra("fragment", 1)
            putExtra("linhas", linhas as Serializable)
        }

        withContext(Main) {
            startActivity(intent)
        }

        //TODO: abrir uma lista com as linhas ao clicar em uma perguntar se quer ver os onibus dessa linha
        //então mostrar os onibus no mapa
    }
//        try {
//            ApiModule.autenticar()
//            val resposta = ApiModule.olhoVivoServices.getLinha(valor)
//            Log.i("retrofit", "recebeu da api: $resposta")
//
//            val intent = Intent(activity, MapsActivity::class.java).apply {
//                //todo: verificar
//                putExtra("linhas", resposta as Serializable)
//            }
//
//            withContext(Main) {
//                startActivity(intent)
//            }
//        } catch (e: Exception) {
//            Log.i("retrofit", "Erro: " + e.message)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}