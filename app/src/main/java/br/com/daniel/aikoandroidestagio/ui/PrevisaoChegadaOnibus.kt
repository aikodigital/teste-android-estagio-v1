package br.com.daniel.aikoandroidestagio.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.adapter.PrevisaoChegadaOnibusAdapter
import br.com.daniel.aikoandroidestagio.databinding.ActivityPrevisaoChegadaOnibusBinding
import br.com.daniel.aikoandroidestagio.model.LX
import br.com.daniel.aikoandroidestagio.util.Constants

class PrevisaoChegadaOnibus : AppCompatActivity() {

    private val binding by lazy { ActivityPrevisaoChegadaOnibusBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = getString(R.string.title_previsao_onibus)

        val parada = intent.getSerializableExtra(Constants.parada) as LX

        binding.nomeLinha.text = parada.c

        binding.rvParadaOnibus.adapter = PrevisaoChegadaOnibusAdapter(parada.vs)
    }
}