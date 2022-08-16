package br.com.daniel.aikoandroidestagio.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.adapter.ListaVeiculosAdapter
import br.com.daniel.aikoandroidestagio.databinding.ActivityListaTodosVeiculosBinding
import br.com.daniel.aikoandroidestagio.enums.From
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos
import br.com.daniel.aikoandroidestagio.ui.maps.MapsActivity
import br.com.daniel.aikoandroidestagio.util.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListaTodosVeiculos : AppCompatActivity() {

    private val binding by lazy { ActivityListaTodosVeiculosBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = "Linhas e ônibus"

        val linhasEonibus = intent.getSerializableExtra(Constants.veic) as LocalizacaoVeiculos?

        linhasEonibus?.let {
            binding.rvLinhasOnibus.adapter = ListaVeiculosAdapter(linhasEonibus)

            binding.buttonOnibusMapa.setOnClickListener {
                iniciaMapsActivity(linhasEonibus)
            }

            binding.buttonFilter.setOnClickListener {
                //Eu não consegui fazer aparecer as opções nesse Dialog
                dialogFiltro()
            }
        } ?: ListaNullErro()
    }

    private fun iniciaMapsActivity(linhasEonibus: LocalizacaoVeiculos?) {
        val intent = Intent(this, MapsActivity::class.java).apply {
            putExtra(Constants.from, From.ONIBUS)
            putExtra(Constants.veic, linhasEonibus)
        }
        startActivity(intent)
    }

    private fun dialogFiltro() {
        val multiItems = arrayOf("Item 1", "Item 2", "Item 3")
        val checkedItems = booleanArrayOf(true, false, false, false)

        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title_filter))
            .setMessage(resources.getString(R.string.supporting_text_filter))
            .setMultiChoiceItems(multiItems, checkedItems) { _, which, checked ->
                // Respond to item chosen
                multiItems[which]
                checkedItems[which] = checked
            }
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.accept_filter)) { dialog, which ->
                // Respond to positive button press
            }
            .create()
            .show()
    }

    private fun ListaNullErro() {
        Toast.makeText(this, getString(R.string.algo_errado_veiculos), Toast.LENGTH_LONG).show()
        finish()
    }
}