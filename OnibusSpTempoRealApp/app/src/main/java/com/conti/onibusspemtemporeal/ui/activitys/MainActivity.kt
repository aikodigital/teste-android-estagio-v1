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


    private fun setupChipSelectCategory() {
        binding.chipLineSelected.setOnClickListener {
            viewModel.getBusRouteSelected(binding.chipLineSelected.text.toString())
        }
    }

    private fun refresh() {
        binding.floatingRefreshBus.setOnClickListener {
            viewModel.getBus()
        }
    }

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


    private fun openSearchActivity() {
        binding.searchBusLines.setOnClickListener {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            val frag = RouteBusSearchDialogFragment()
            frag.show(ft, "txn_tag")
        }
    }


}