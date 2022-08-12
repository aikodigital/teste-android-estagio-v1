package br.com.daniel.aikoandroidestagio.ui.main.linhas

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.daniel.aikoandroidestagio.databinding.FragmentLinhasBinding
import br.com.daniel.aikoandroidestagio.model.Linha
import br.com.daniel.aikoandroidestagio.network.ApiModule
import br.com.daniel.aikoandroidestagio.ui.main.MainActivity
import br.com.daniel.aikoandroidestagio.ui.maps.MapsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                configuraBuscaPorLinha(valor)
            }
        }

        val root: View = binding.root
        return root
    }

    private suspend fun configuraBuscaPorLinha(valor: Editable) {
        try {
            val resposta = ApiModule.olhoVivoServices.getLinha(valor.toString())
            Log.i("retrofit", resposta.toString())

            val intent = Intent(activity, MapsActivity::class.java).apply {
                //todo: verificar
                putExtra("linhas", resposta as ArrayList)
            }

            startActivity(intent)
        } catch (e: Exception) {
            lifecycleScope.launch(Dispatchers.Main) {
                Toast.makeText(context, "Erro: " + e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}