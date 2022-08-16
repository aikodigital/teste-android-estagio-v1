package br.com.daniel.aikoandroidestagio.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


object Utils {
    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    suspend fun pegarRuaMaisProxima(context: Context): String {

        var addressLine = ""
                        CoroutineScope(Dispatchers.IO).launch {
                    val enderecos = Geocoder(context).getFromLocation(-23.5454758, -46.6455341,1)
                    delay(500)
                    if (enderecos != null && enderecos.size > 0) {
                        val endereco: Address = enderecos[0]
                        addressLine = endereco.getAddressLine(0)
                    }
                }
        return addressLine
    }
}