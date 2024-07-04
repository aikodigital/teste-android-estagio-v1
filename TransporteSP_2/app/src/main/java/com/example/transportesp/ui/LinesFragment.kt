package com.example.transportesp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
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

class LinesFragment : Fragment() {

    private lateinit var apiService: OlhoVivoApi
    private lateinit var recyclerView: RecyclerView
    private lateinit var linesAdapter: LinesAdapter
    private lateinit var editTextSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lines, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewLines)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        editTextSearch = view.findViewById(R.id.editTextSearch)
        val buttonSearch = view.findViewById<Button>(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            fetchBusLines()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiService = RetrofitInstance.api
        fetchBusLines()
    }

    private fun fetchBusLines() {
        val token = "2c6db6c4a0a1eef270b335bfe67e134326a507615ff86d699e6333afa16d85cf"

        lifecycleScope.launch {
            try {
                val searchTerm = editTextSearch.text.toString().trim() // Obtém o texto do EditText
                val busLines = withContext(Dispatchers.IO) {
                    apiService.buscarLinhas(token, searchTerm.ifEmpty { "Lapa" })
                }
                linesAdapter = LinesAdapter(busLines)
                recyclerView.adapter = linesAdapter
            } catch (e: Exception) {

            }
        }
    }
}
