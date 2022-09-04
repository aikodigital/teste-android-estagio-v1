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
import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.databinding.ActivityMainBinding
import com.conti.onibusspemtemporeal.ui.fragments.route.RouteBusSearchDialogFragment
import com.conti.onibusspemtemporeal.ui.viewModel.OnibusSpViewModel
import com.conti.onibusspemtemporeal.ui.viewModel.UiStateBusRoute
import com.conti.onibusspemtemporeal.util.retrofitHandling.Resource
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

        setupPopupmenuHistory()
        openSearchActivity()
        observerUiState()

    }

    private fun setupPopupmenuHistory() {
        binding.buttonOpenHistory.setOnClickListener {

            val popupMenu = PopupMenu(this, it)

            viewModel.favoritesBusRoute.observe(this) { listBusRoute ->

                popupMenu.menu.clear()

                popupMenu.menuInflater.inflate(R.menu.menu_popup_historico, popupMenu.menu)

                listBusRoute.forEach {
                    popupMenu.menu.add("${it.lineCod}")
                }

            }

            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { item ->

                viewModel.selectTheBusRoute(item.title.toString().toInt())

                true
            }
        }


    }

    private fun observerUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { uiState ->

                    when {
                        uiState.message.isNotEmpty() -> {
                            Toast.makeText(this@MainActivity, uiState.message, Toast.LENGTH_LONG)
                                .show()
                            viewModel.clearMessages()
                        }
                        uiState.lineCod >= 0 -> {
                            setChip(uiState.lineCod)
                        }

                    }
                }

            }
        }
    }

    private fun setChip(currentLineCod: Int) {

        with(binding.chipLineSelected) {

            isVisible = true
            text = currentLineCod.toString()

            setOnCloseIconClickListener {
                it.isInvisible = true
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