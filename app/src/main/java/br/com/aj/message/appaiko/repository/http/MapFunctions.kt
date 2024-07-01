package br.com.aj.message.appaiko.repository.http

import android.content.Context
import br.com.aj.message.appaiko.R
import br.com.aj.message.appaiko.createServiceHttp
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MapFunctionsHttpRepository(val ctx: Context,) {

        suspend fun createRequestMap(
            origin: LatLng,
            dest: LatLng,
        ): ArrayList<List<LatLng>> {

            val originString = origin.run { "${latitude},${longitude}" }
            val destString = dest.run { "${latitude},${longitude}" }
            val http = createServiceHttp(ctx, "https://maps.googleapis.com")
            val array = withContext(Dispatchers.IO) {

                val mapData = http.mapRoute(
                    originString,
                    destString,
                    "false",
                    "driving",
                    ctx.getString(R.string.keydirecti)
                )

                val result = ArrayList<List<LatLng>>()

                val path = ArrayList<LatLng>()
                for (i in 0 until mapData.routes[0].legs[0].steps.size) {
                    path.addAll(decodePolyline(mapData.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
              return@withContext  result
            }

            return array
        }


      private  fun decodePolyline(encoded: String): List<LatLng> {
            val poly = ArrayList<LatLng>()
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0
            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0
                do {
                    b = encoded[index++].code - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lat += dlat
                shift = 0
                result = 0
                do {
                    b = encoded[index++].code - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lng += dlng
                val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
                poly.add(latLng)
            }
            return poly
        }


    }

