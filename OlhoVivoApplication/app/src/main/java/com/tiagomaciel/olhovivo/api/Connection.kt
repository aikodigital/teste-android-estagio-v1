package com.tiagomaciel.olhovivo.api

import android.util.Log
import com.tiagomaciel.olhovivo.api.dataClass.StopLocation
import com.tiagomaciel.olhovivo.api.dataClass.VehicleLines
import com.tiagomaciel.olhovivo.api.dataClass.VehiclePosition
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

    fun authenticateAndFetchData(
        onResultVehiclePosition: (VehiclePosition?) -> Unit,
        onResultVehicleLines: (List<VehicleLines>?) -> Unit,
        onResultVehicleStops: (List<StopLocation>?) -> Unit,
        type: String,
        searchTerms: String
    ) {
        apiService.authenticate(token).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful && response.body() == true) {
                    Log.d("API", "Authenticated successfully")
                    fetchVehiclePositions(onResultVehiclePosition)
                    fetchBusLines(onResultVehicleLines, searchTerms)
                    fetchVehicleStops(onResultVehicleStops, searchTerms)
                } else {
                    Log.e("API", "Authentication failed")
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
            }
        })
    }

    fun fetchVehiclePositions(onResult: (VehiclePosition?) -> Unit) {
        apiService.getVehiclePositions().enqueue(object : Callback<VehiclePosition> {
            override fun onResponse(call: Call<VehiclePosition>, response: Response<VehiclePosition>) {
                Log.d("API", "Response code: ${response.code()}")
                if (response.isSuccessful && response.body() != null) {
                    val positions = response.body()
                    onResult.invoke(positions)
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

    fun fetchBusLines(onResult: (List<VehicleLines>?) -> Unit, searchTerms: String) {
        apiService.getBusLines(searchTerms).enqueue(object : Callback<List<VehicleLines>?> {
            override fun onResponse(call: Call<List<VehicleLines>?>, response: Response<List<VehicleLines>?>) {
                Log.d("API", "Response code: ${response.code()}")
                if (response.isSuccessful && response.body() != null) {
                    val lines = response.body()
                    onResult.invoke(lines)
                    Log.d("API", "Bus Lines: $lines")
                } else {
                    Log.e("API", "Failed to fetch bus lines. Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<VehicleLines>?>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
            }
        })
    }

    fun fetchVehicleStops(onResult: (List<StopLocation>?) -> Unit, searchTerms: String) {
        apiService.getVehicleStops(searchTerms).enqueue(object : Callback<List<StopLocation>?> {
            override fun onResponse(call: Call<List<StopLocation>?>, response: Response<List<StopLocation>?>) {
                Log.d("API", "Response code: ${response.code()}")
                if (response.isSuccessful && response.body() != null) {
                    val lines = response.body()
                    onResult.invoke(lines)
                    Log.d("API", "Bus Lines: $lines")
                } else {
                    Log.e("API", "Failed to fetch bus lines. Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<StopLocation>?>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
            }
        })
    }
}
