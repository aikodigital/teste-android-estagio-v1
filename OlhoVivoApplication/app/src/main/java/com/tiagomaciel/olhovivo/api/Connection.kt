package com.tiagomaciel.olhovivo.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager {
    private val token = "2fc1e08c1039531d8970bc3f066b0487f45722ebb53d590878506623f677cb9e"
    private val apiService: ApiService

    init {
        val retrofit = ApiClient.client
        apiService = retrofit.create(ApiService::class.java)
    }

    fun authenticateAndFetchData(onResult: (VehiclePosition?) -> Unit) {
        apiService.authenticate(token).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() == true) {
                    Log.d("API", "Authenticated successfully")
                    fetchVehiclePositions(onResult)
                } else {
                    Log.e("API", "Authentication failed")
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
            }
        })
    }

    private fun fetchVehiclePositions(onResult: (VehiclePosition?) -> Unit) {
        apiService.getVehiclePositions().enqueue(object : Callback<VehiclePosition> {
            override fun onResponse(call: Call<VehiclePosition>, response: Response<VehiclePosition>) {
                Log.d("API", "Response code: ${response.code()}")
                if (response.isSuccessful && response.body() != null) {
                    val positions = response.body()
                    onResult.invoke(positions as? VehiclePosition)
                    Log.d("API", "Positions: $positions")
                } else {
                    Log.e("API", "Failed to fetch vehicle positions. Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<VehiclePosition>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
            }
        })
    }
}
