package br.com.daniel.aikoandroidestagio.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import br.com.daniel.aikoandroidestagio.databinding.ActivityListaTodosVeiculosBinding
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos

private val TAG = "DEBUG"

class ListaTodosVeiculos : AppCompatActivity() {

    private val binding by lazy { ActivityListaTodosVeiculosBinding.inflate(LayoutInflater.from(this)) }

    /**
     * Nessa tela vou mostrar uma lista de veiculos por linha
     * e vai ter um botão para ver os veiculos no mapa
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val linhasEonibus = intent.getSerializableExtra("veiculos") as LocalizacaoVeiculos


        //todo: deletar depois que testar
        if (!linhasEonibus.isEmpty()) {
            linhasEonibus.l.forEachIndexed { index, linha ->
                Log.d(TAG, "--------------------Linha: $index")
                    val destino = linha.lt0
                    linha.vs.forEachIndexed { index, veiculo ->
                        Log.d(TAG, "-Veiculo: $index")
                        apontarNoMapa(veiculo.px,veiculo.py, veiculo.p, destino)
                    }
                }
            }
        }
    }


fun apontarNoMapa(px: Double, py: Double, idV: Int, destino: String) {
    Log.d(TAG, "apontarNoMapa: $px $py $idV $destino")
}