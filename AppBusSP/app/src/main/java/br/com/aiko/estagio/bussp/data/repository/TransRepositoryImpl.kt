package br.com.aiko.estagio.bussp.data.repository

import android.util.Log
import br.com.aiko.estagio.bussp.data.remote.RemoteDataSource
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
}
