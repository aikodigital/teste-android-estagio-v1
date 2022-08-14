package br.com.daniel.aikoandroidestagio.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.adapter.ListaVeiculosAdapter
import br.com.daniel.aikoandroidestagio.databinding.ActivityListaTodosVeiculosBinding
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos
import br.com.daniel.aikoandroidestagio.ui.maps.MapsActivity
import br.com.daniel.aikoandroidestagio.util.Constants

private val TAG = "DEBUG"

class ListaTodosVeiculos : AppCompatActivity() {

    private val binding by lazy { ActivityListaTodosVeiculosBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val linhasEonibus = intent.getSerializableExtra(Constants.veic) as LocalizacaoVeiculos?

        linhasEonibus?.let {
            if (!it.isEmpty()) {
                binding.rvLinhasOnibus.adapter = ListaVeiculosAdapter(linhasEonibus)
            } else {
                ListaNullErro()
            }

            binding.buttonOnibusMapa.setOnClickListener {
                val intent = Intent(this, MapsActivity::class.java).apply {
                    putExtra(Constants.from, 4)
                    putExtra(Constants.veic, linhasEonibus)
                }
                startActivity(intent)
            }

        } ?: ListaNullErro()


//        //todo: deletar depois que testar
//        if (!linhasEonibus.isEmpty()) {
//            linhasEonibus.l.forEachIndexed { index, linha ->
//                Log.d(TAG, "--------------------Linha: $index")
//                    val destino = linha.lt0
//                    linha.vs.forEachIndexed { index, veiculo ->
//                        Log.d(TAG, "-Veiculo: $index")
//                        apontarNoMapa(veiculo.px,veiculo.py, veiculo.p, destino)
//                    }
//                }
//            }
        }

    private fun ListaNullErro() {
        Toast.makeText(this, getString(R.string.algo_errado), Toast.LENGTH_LONG).show()
        finish()
    }
}


//private fun apontarNoMapa(px: Double, py: Double, idV: Int, destino: String) {
//    Log.d(TAG, "apontarNoMapa: $px $py $idV $destino")
//}