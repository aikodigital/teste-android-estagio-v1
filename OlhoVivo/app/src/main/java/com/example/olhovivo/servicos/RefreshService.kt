package com.example.olhovivo.servicos

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import com.example.olhovivo.repository.CorredoresRepository
import com.example.olhovivo.repository.LinhaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RefreshService : Service() {
    private val refreshInterval: Long = 5 * 60 * 1000
    private val handler = Handler(Looper.getMainLooper())

    private val refreshRunnable = object : Runnable {
        override fun run() {
            updateData()
            handler.postDelayed(this, refreshInterval)
        }
    }

    override fun onCreate() {
        super.onCreate()
        handler.post(refreshRunnable)
    }

    override fun onDestroy() {
        handler.removeCallbacks(refreshRunnable)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun updateData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val corredoresRepository = CorredoresRepository(/* Passar a instância do API ou usar uma fábrica */)
//                val linhaRepository = LinhaRepository(/* Passar a instância do API ou usar uma fábrica */)
//
//                val corredores = corredoresRepository.obterCorredores()
//                val linhas = linhaRepository.getLinhas("Nome da Linha")
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(this@RefreshService, "Dados atualizados", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // Lidar com exceções e erros
//            }
//        }
    }
}
