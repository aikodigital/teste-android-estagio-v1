package br.com.daniel.aikoandroidestagio.ui.main.paradas

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.daniel.aikoandroidestagio.databinding.FragmentParadasBinding
import br.com.daniel.aikoandroidestagio.ui.maps.MapsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        _binding?.buttonPonto!!.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java).apply {
                putExtra("fragment", 3)
            }
            startActivity(intent)

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