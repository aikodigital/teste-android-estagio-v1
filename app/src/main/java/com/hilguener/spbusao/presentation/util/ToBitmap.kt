package com.hilguener.spbusao.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun Int.toBitmap(
    context: Context,
    @ColorRes tintColor: Int? = null,
): Bitmap? {
    val drawable = ContextCompat.getDrawable(context, this) ?: return null

    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm =
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888,
        )

    tintColor?.let {
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, it))
    }

    val canvas = Canvas(bm)
    drawable.draw(canvas)

    return bm
}
