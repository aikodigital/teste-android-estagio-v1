package br.com.aiko.estagio.bussp.data.repository

import android.util.Log
import br.com.aiko.estagio.bussp.data.remote.RemoteDataSource
import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TransRepository {

    override suspend fun authentication(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.authentication(token)
                if (response.isSuccessful) {
                    response.body() ?: false
                } else {
                    Log.e("Authentication", "${response.code()}")
                    false
                }
            } catch (e: Exception) {
                Log.e("Authentication", e.message.toString())
                false
            }
        }
    }

    override suspend fun buscarLinha(termoBuscar: String): List<Linha> {
        val response = remoteDataSource.buscarLinha(termoBuscar)

        return try {
            if (response.isSuccessful) {
                val linhas = response.body() ?: emptyList()
                return linhas
            } else {
                Log.e("BUSCAR LINHA", "${response.code()}")
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR LINHA Execptin: ", e.message.toString())
            emptyList<Linha>()
        }
    }

    override suspend fun buscarLinhaSentido(termosBusca: String, sentido: Int): List<Linha> {
        val response = remoteDataSource.buscarLinhaSentido(termosBusca, sentido)
        return try {
            if (response.isSuccessful) {
                val linhas = response.body() ?: emptyList()
                linhas
            } else {
                Log.e("BUSCAR LINHA SENTINDO", "${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR LINHA SENTINDO Execption: ", e.message.toString())
            emptyList<Linha>()
        }
    }

    override suspend fun buscarParada(parada: String): List<Parada> {
        val response = remoteDataSource.buscarParada(parada)
        return try {
            if (response.isSuccessful) {
                val paradas = response.body() ?: emptyList()
                paradas
            } else {
                Log.e("BUSCAR PARADA", "${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR PARADA Exception: ", e.message.toString())
            emptyList<Parada>()
        }
    }

    override suspend fun buscarParadasPorLinha(codigoLinha: String): List<Parada> {
        val reponse = remoteDataSource.buscarParadasPorLinha(codigoLinha)
        return try {
            if (reponse.isSuccessful) {
                val paradas = reponse.body() ?: emptyList()
                paradas
            } else {
                Log.e("BUSCAR PARADAS POR LINHA", "${reponse.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR PARADAS POR LINHA Exception: ", e.message.toString())
            emptyList<Parada>()
        }
    }

    override suspend fun buscarParadasPorCorredor(codigoCorredor: Int): List<Parada> {
        val response = remoteDataSource.buscarParadasPorCorredor(codigoCorredor)
        return try {
            if (response.isSuccessful) {
                val paradas = response.body() ?: emptyList()
                paradas
            } else {
                Log.e("BUSCAR PARADA POR CORREDOR", "${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR PARADA POR CORREDOR Exception", e.message.toString())
            emptyList<Parada>()
        }
    }
}
