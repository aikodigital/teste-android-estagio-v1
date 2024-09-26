package com.aiko.data.remote.adapters.internal

import android.content.ContentValues.TAG
import android.util.Log
import com.aiko.domain.network.ErrorBody
import com.aiko.domain.network.NetworkResult
import okhttp3.Request
import okio.Timeout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<NetworkResult<T>> {
    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                var networkResult: NetworkResult<T>
                try {
                    networkResult = if (response.isSuccessful && body != null) {
                        NetworkResult.Success(body)
                    } else {
                        val code = response.code()
                        val errorBodyStr = response.errorBody()?.string()
                        Log.e(TAG, "Erro enqueue: $errorBodyStr")

                        val errorMessage = try {
                            val json = JSONObject(errorBodyStr ?: "")
                            json.getString("error")
                        } catch (e: Exception) {
                            "Unknown error"
                        }

                        NetworkResult.Error(code, ErrorBody(errorMessage))
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Exception NetworkResultCall: $e")
                    networkResult = NetworkResult.Exception(e)
                }

                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = NetworkResult.Exception<T>(t)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }
        })
    }

    override fun execute(): Response<NetworkResult<T>> = throw NotImplementedError()
    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone())
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() {
        proxy.cancel()
    }
}
