package com.conti.onibusspemtemporeal.ui.activitys

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.databinding.ActivityMainBinding
import com.conti.onibusspemtemporeal.ui.viewModel.OnibusSpViewModel
import com.conti.onibusspemtemporeal.util.retrofitHandling.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: OnibusSpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        openPop()
        teste()
    }

    private fun teste() {

        //testando ainda
        viewModel.routersBus.observe(this) { routersBusResponse ->
            when (routersBusResponse) {
                is Resource.Success -> {
                    routersBusResponse.data.let {
                        val list: MutableList<BusRoute> = mutableListOf()

                        list.addAll(it!!.toMutableList())
                        for (route in list) {
                            Toast.makeText(
                                this,
                                "Line code: ${route.lineCod}}, main terminal : ${route.mainTerminal}, second terminal: ${route.secondTerminal}, ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                is Resource.Error -> {

                    Toast.makeText(this, "erro->${routersBusResponse.message}", Toast.LENGTH_LONG).show()
                }
                else -> {

                }
            }
        }

    }

    private fun openPop() {
        binding.buttonOpenHistory.setOnClickListener {

            val popupMenu = PopupMenu(this, it)

            popupMenu.menuInflater.inflate(R.menu.menu_popup_historico, popupMenu.menu)

            popupMenu.show()
        }

    }


}