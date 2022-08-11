package br.com.daniel.aikoandroidestagio.ui.main.linhas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.daniel.aikoandroidestagio.databinding.FragmentLinhasBinding

class LinhasFragment : Fragment() {

    private var _binding: FragmentLinhasBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(LinhasViewModel::class.java)
        _binding = FragmentLinhasBinding.inflate(inflater, container, false)

        val valor = _binding?.textPosicaoLinha?.editText!!.text

        _binding?.buttonBuscarLinha?.setOnClickListener {
            Toast.makeText(context, valor, Toast.LENGTH_LONG).show()
        }


        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}