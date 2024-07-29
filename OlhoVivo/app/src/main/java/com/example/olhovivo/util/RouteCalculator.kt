package com.example.olhovivo.util

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class RouteCalculator(private val context: Context) {

    private val client = OkHttpClient()

    fun calculateRoute(origin: LatLng, destination: LatLng, callback: (List<LatLng>) -> Unit) {
        val apiKey = BuildConfig.MAPS_API_KEY
        val url = "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${destination.latitude},${destination.longitude}&key=$apiKey"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body?.string()
                    val routePoints = parseRoute(jsonResponse)
                    callback(routePoints)
                } else {
                    // Tratar erros da resposta
                }
            }
        })
    }

    private fun parseRoute(response: String?): List<LatLng> {
        val points = mutableListOf<LatLng>()
        val json = JSONObject(response)
        val routes = json.getJSONArray("routes")
        if (routes.length() > 0) {
            val legs = routes.getJSONObject(0).getJSONArray("legs")
            if (legs.length() > 0) {
                val steps = legs.getJSONObject(0).getJSONArray("steps")
                for (i in 0 until steps.length()) {
                    val step = steps.getJSONObject(i)
                    val startLocation = step.getJSONObject("start_location")
                    val endLocation = step.getJSONObject("end_location")
                    points.add(LatLng(startLocation.getDouble("lat"), startLocation.getDouble("lng")))
                    points.add(LatLng(endLocation.getDouble("lat"), endLocation.getDouble("lng")))
                }
            }
        }
        return points
    }
}
