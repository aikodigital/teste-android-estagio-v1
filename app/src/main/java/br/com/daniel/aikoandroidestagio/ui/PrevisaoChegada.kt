package br.com.daniel.aikoandroidestagio.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.adapter.PrevisaoChegadaLinhasAdapter
import br.com.daniel.aikoandroidestagio.databinding.ActivityPrevisaoChegadaBinding
import br.com.daniel.aikoandroidestagio.model.*
import br.com.daniel.aikoandroidestagio.model.PrevisaoChegada
import br.com.daniel.aikoandroidestagio.services.ApiModule
import br.com.daniel.aikoandroidestagio.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PrevisaoChegada : AppCompatActivity() {
    private val binding by lazy { ActivityPrevisaoChegadaBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = getString(R.string.title_previsao_linhas)

        val parada = intent.getSerializableExtra(Constants.parada) as Parada
        Log.d("debug", "Previsao chegada: recebeu: $parada")
        binding.nomeParada.text = parada.nome

        lifecycleScope.launch {
            val resposta = ApiModule.getPrevisao(parada.id)
            if (resposta.isSuccessful) {
                val previsaoChegada = resposta.body()

                Log.d("debug", "Previsao chegada: resposta: $previsaoChegada")

                previsaoChegada?.p?.let { p ->
                    binding.rvLinhasParada.adapter = PrevisaoChegadaLinhasAdapter(p)
                }

                if (previsaoChegada?.p == null) {

                    //todo: deletar esse teste
                    binding.rvLinhasParada.adapter = PrevisaoChegadaLinhasAdapter(
                        P(
                            2, "s", 12.0, 25.3, listOf(
                                LX(
                                    "123", 12, 25, "aaaa", "bbbb", 1, listOf(
                                        VX("wewer", "fddfdg", true, "sdgerg", 58.1, 47.5),
                                        VX("we342wer", "fddf243dg", true, "sdge234rg", 58.1, 47.5)
                                    )
                                ),
                                LX(
                                    "123434233", 12, 25, "aasdfaa", "bbbsfdb", 1, listOf(
                                        VX("wesdfwer", "fddfsdffdg", true, "sdgesdfdrg", 58.1, 47.5),
                                        VX("we342sdfsdfwer", "fsdfddf243dg", true, "sdgsdffe234rg", 58.1, 47.5)
                                    )
                                )
                            )
                        )
                    )

                    Toast.makeText(
                        this@PrevisaoChegada,
                        getString(R.string.nao_tem_onibus),
                        Toast.LENGTH_LONG
                    ).show()
//                    finish()
                }

            } else {
                Toast.makeText(
                    this@PrevisaoChegada,
                    getString(R.string.algo_errado),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }
}