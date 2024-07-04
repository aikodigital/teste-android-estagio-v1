package com.example.transportesp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transportesp.R
import com.example.transportesp.http.OlhoVivoApi
import com.example.transportesp.http.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class searchFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var checkBoxPrevisoes: CheckBox
    private lateinit var checkBoxParadas: CheckBox
    private lateinit var checkBoxVeiculos: CheckBox
    private lateinit var applyFilterButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataAdapter
    private lateinit var dataList: MutableList<String>
    private lateinit var apiService: OlhoVivoApi
    private var token = "2c6db6c4a0a1eef270b335bfe67e134326a507615ff86d699e6333afa16d85cf"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchEditText = view.findViewById(R.id.searchEditText)
        checkBoxPrevisoes = view.findViewById(R.id.checkBoxPrevisoes)
        checkBoxParadas = view.findViewById(R.id.checkBoxParadas)
        applyFilterButton = view.findViewById(R.id.applyFilterButton)
        recyclerView = view.findViewById(R.id.recyclerView)

        dataList = mutableListOf()
        adapter = DataAdapter(dataList)
        apiService = RetrofitInstance.api


        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        recyclerView.adapter = adapter

        applyFilterButton.setOnClickListener {
            applyFilter()
        }

        return view
    }

    private fun applyFilter() {
        val query = searchEditText.text.toString()
        val showPrevisoes = checkBoxPrevisoes.isChecked
        val showParadas = checkBoxParadas.isChecked


        lifecycleScope.launch(Dispatchers.IO) {
            val filteredList = mutableListOf<String>()

            try {
                if (showPrevisoes) {
                    val previsoes = apiService.buscarPrevisoesChegada("Bearer $token", query.toInt())
                    for (linePrediction in previsoes.p.l) {
                        for (vehiclePrediction in linePrediction.vs) {
                            filteredList.add("Linha: ${linePrediction.lt0}, Veículo: ${vehiclePrediction.p}, Chegada: ${vehiclePrediction.t}")
                        }
                    }
                }
                if (showParadas) {
                    val paradas = apiService.buscarParadas("Bearer $token", query)
                    paradas.forEach {
                        filteredList.add("Parada: ${it.np}, Endereço: ${it.ed}")
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                }
            }

            withContext(Dispatchers.Main) {
                adapter.setData(filteredList)
            }
        }
    }
}
