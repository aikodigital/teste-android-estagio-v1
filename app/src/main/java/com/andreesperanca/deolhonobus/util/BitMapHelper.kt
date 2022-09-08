package com.andreesperanca.deolhonobus.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.DrawableContainer
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object BitMapHelper {


    @SuppressLint("ResourceAsColor")
    fun vectorToBitMap(
        context: Context,
        @DrawableRes id: Int,
        color: Int
    ) : BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(context.resources,id,null)
        return if (vectorDrawable == null) {
            BitmapDescriptorFactory.defaultMarker()
        } else {
            val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.setBounds(0,0,canvas.width,canvas.height)
            DrawableCompat.setTint(vectorDrawable,color)
            vectorDrawable.draw(canvas)
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }

    }
}