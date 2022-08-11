package br.com.daniel.aikoandroidestagio.ui.main.posicao

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.daniel.aikoandroidestagio.databinding.FragmentPosicaoBinding

class posicaoFragment : Fragment() {

    private var _binding: FragmentPosicaoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val posicaoViewModel =  ViewModelProvider(this).get(PosicaoViewModel::class.java)

        _binding = FragmentPosicaoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}