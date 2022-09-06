package com.conti.onibusspemtemporeal.ui.activitys

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.conti.onibusspemtemporeal.R

import com.conti.onibusspemtemporeal.databinding.ActivityMainBinding
import com.conti.onibusspemtemporeal.ui.fragments.route.RouteBusSearchDialogFragment
import com.conti.onibusspemtemporeal.ui.viewModel.OnibusSpViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: OnibusSpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupPopupmenuFavorite()
        openSearchActivity()
        observerUiState()
        setupChipSelectCategory()
        refresh()

    }


    /** Função para realizar uma busca da linha selecionada caso o chip da linha sejá clicado*/
    private fun setupChipSelectCategory() {
        binding.chipLineSelected.setOnClickListener {
            viewModel.getBusRouteSelected(binding.chipLineSelected.text.toString())
        }
    }

    /** Função para atualizar os ônibus chamando a função getBus do viewModel*/
    private fun refresh() {
        binding.floatingRefreshBus.setOnClickListener {
            viewModel.getBus()
        }
    }

    /** Função para consumir um LiveData das linhas que foram favoritadas e salvas no Room,
     * ao clickar no floating button, criei um popupMenu e adiciono no popupMenu todos
     * os itens da lista de linhas que foram consumidas do live data, com o título sendo o letreiro completo da linha
     * e caso selecione alguma item do menu, envio pra função do viewModel, a linha selecionada*/
    private fun setupPopupmenuFavorite() {
        binding.floatingButtonFavorite.setOnClickListener {

            val popupMenu = PopupMenu(this, it)

            viewModel.favoritesBusRoute.observe(this) { listBusRoute ->

                popupMenu.menu.clear()

                popupMenu.menuInflater.inflate(R.menu.menu_popup_historico, popupMenu.menu)

                listBusRoute.forEach { busRoute ->
                    popupMenu.menu.add("${busRoute.firstNumbersPlacard}-${busRoute.secondPartPlacard}")
                }
            }

            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { item ->

                viewModel.getBusRouteSelected(item.toString())

                true
            }
        }
    }

    /** Função para observar o [uiState]
     *  para seguir as boas praticas e recomendações do google na forma de coletar um stateFlow,
     *  utilizei do lifecycleScope e o repeatOnLifecycle, caso o stateFlow [uiState] mude seu valor
     *  o chip da quantidade de onibus vai mudar, se tiver messagem de error mostrar ele em uma Toast
     *  e se tiver um codigo de linha, chamar setChip enviando o codigo da linha */
    private fun observerUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { uiState ->

                    binding.chipQuantityBus.text = uiState.quantityBusInThisRoute.toString()

                    when {
                        uiState.message.isNotEmpty() -> {
                            Toast.makeText(this@MainActivity, uiState.message, Toast.LENGTH_LONG)
                                .show()
                            viewModel.clearMessages()
                        }
                        uiState.lineCod.isNotEmpty() -> {
                            setChip(uiState.lineCod)
                        }

                    }
                }
            }
        }
    }

    /** Função para configurar o Chip da Linha atual,
     * torna o chip visivel e com a [currentLineCod] coloco no .text do chip
     * caso o botao do close for clickado, chip fica invisvel e chamo duas funções do viewmodel
     * retirar a linha atual, e pedir pra buscar todos so ônibus*/
    private fun setChip(currentLineCod: String) {

        with(binding.chipLineSelected) {

            isVisible = true
            text = currentLineCod

            setOnCloseIconClickListener {
                it.isInvisible = true
                viewModel.clearLineCode()
                viewModel.getBus()
            }

        }
    }


    /** Função para abrir o dialog fragment de busca de linhas de onibus*/
    private fun openSearchActivity() {
        binding.searchBusLines.setOnClickListener {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            val routeBusSearchDialogFragment = RouteBusSearchDialogFragment()
            routeBusSearchDialogFragment.show(ft, "Dialog_route_bus")
        }
    }


}