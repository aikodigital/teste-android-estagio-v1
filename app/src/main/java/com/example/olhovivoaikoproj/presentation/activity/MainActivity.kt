package com.example.olhovivoaikoproj.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.olhovivoaikoproj.R
import com.example.olhovivoaikoproj.databinding.ActivityMainBinding
import com.example.olhovivoaikoproj.di.adapterModule
import com.example.olhovivoaikoproj.presentation.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val adapter: Adapter by inject()
    private lateinit var binding: ActivityMainBinding

    private var textoBusca: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }

    private fun initRecyclerView(){
        binding.rvLinhas.layoutManager = LinearLayoutManager(this)
        binding.rvLinhas.setHasFixedSize(true)
        binding.rvLinhas.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getSearch()
        mainViewModel.getAuthenticateToken(
            onSucess =  {
            },
            onFailure =  {
            }
        )
    }

    private fun getLinhas() {

        mainViewModel.getLinhas(
            termosBuscas = textoBusca,
            onSucess = {
                adapter.setListLinhas(it)
            },
            onFailure =  {

            }
        )
    }

    private fun getSearch(){
        binding.buttonBuscar.setOnClickListener {
            textoBusca = binding.searchMain.text.toString()
            //Toast.makeText(this, "O texto foi: $textoBusca", Toast.LENGTH_LONG).show()
            getLinhas()
            binding.searchMain.text.clear()

        }
    }

}
