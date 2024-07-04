package com.breno.testeandroidestagiov1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.breno.testeandroidestagiov1.databinding.ActivityResultBinding
import org.json.JSONArray


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString = intent.getStringExtra("result")
        jsonString?.let {
            displayResults(it)
        }
    }

    private fun displayResults(jsonString: String) {
        val jsonArray = JSONArray(jsonString)
        val stringBuilder = StringBuilder()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            stringBuilder.append("Linha: ${jsonObject.getString("lt")}\n")
            stringBuilder.append("Sentido: ${jsonObject.getString("ts")}\n")
            stringBuilder.append("Destino: ${jsonObject.getString("tp")}\n")
            stringBuilder.append("\n")
        }
        binding.resultTextView.text = stringBuilder.toString()
    }
}