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

class ListaTodosVeiculos : AppCompatActivity() {

    private val binding by lazy { ActivityListaTodosVeiculosBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val linhasEonibus = intent.getSerializableExtra(Constants.veic) as LocalizacaoVeiculos?

        val nulle = (linhasEonibus == null)
        Log.d("debug", "onCreate: $nulle")

        linhasEonibus?.let {
            binding.rvLinhasOnibus.adapter = ListaVeiculosAdapter(linhasEonibus)

            binding.buttonOnibusMapa.setOnClickListener {
                val intent = Intent(this, MapsActivity::class.java).apply {
                    putExtra(Constants.from, 4)
                    putExtra(Constants.veic, linhasEonibus)
                }
                startActivity(intent)
            }

        } ?: ListaNullErro()

    }

    private fun ListaNullErro() {
        Toast.makeText(this, getString(R.string.algo_errado_veiculos), Toast.LENGTH_LONG).show()
        finish()
    }
}