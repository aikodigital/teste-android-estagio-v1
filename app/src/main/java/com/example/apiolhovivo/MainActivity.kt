package com.example.apiolhovivo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import classes.*
import com.example.apiolhovivo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txtSearch: EditText = findViewById(R.id.edttxt_Search)
        val txtAviso: TextView = findViewById(R.id.txt_noResult)

        txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loadLinhas(txtSearch, txtAviso)
                true
            } else {
                false
            }
        }
    }

    private fun loadLinhas(txtSearch: EditText, txtAviso: TextView)
    {
        lifecycleScope.launch {
            try {
                val data = validation()

                println(data)

                var termo = txtSearch.text.toString()
                var linhas = getLinhas(termo)

                if (linhas.size > 0) {
                    initRecyclerView(linhas)
                    txtAviso.visibility = View.INVISIBLE
                }
                else txtAviso.visibility = View.VISIBLE

            } catch (e: Exception) {
                txtAviso.visibility = View.VISIBLE
                txtAviso.setText("Erro na conexão")
            }
        }

        hideKeyboard(this)
    }


    private fun initRecyclerView(lista: List<Linha>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        val ItemAdapter = adapterLinhas(lista)
        binding.recyclerView.adapter = ItemAdapter

        ItemAdapter.setOnClickListener(object :
            adapterLinhas.OnClickListener {
            override fun onClick(position: Int, model: Linha) {
                val intent = Intent(this@MainActivity, MapsActivity::class.java)

                intent.putExtra("codigoLinha", model.cl.toString())
                startActivity(intent)
            }
        })
    }

    fun changeToMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

}