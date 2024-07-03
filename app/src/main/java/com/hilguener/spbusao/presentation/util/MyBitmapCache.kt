package com.hilguener.spbusao.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

class MyBitmapCache(
    val context: Context,
    size: Int,
) {
    private val cache = LruCache<Int, Bitmap>(size)

    private fun hash(
        drawable: Int,
        color: Int? = null,
    ): Int {
        var hash = 17
        hash = hash * 31 + drawable
        hash = hash * 31 + (color ?: 0)

        return hash
    }

    fun getBitmap(
        @DrawableRes drawable: Int,
        @ColorRes tintColor: Int? = null,
    ): Bitmap? {
        val currentHash = hash(drawable, tintColor ?: 0)

        if (cache[currentHash] == null) {
            drawable.toBitmap(context, tintColor)?.let {
                cache.put(currentHash, it)
            }
        }

        Log.d("HSV", "Hits:${cache.hitCount()} Misses:${cache.missCount()}")

        return cache[currentHash]
    }
}
