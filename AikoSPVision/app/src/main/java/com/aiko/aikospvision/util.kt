package com.aiko.aikospvision

import com.aiko.domain.model.StopModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object util {
    val moshi = Moshi.Builder().add(
        KotlinJsonAdapterFactory()
    ).build()
    val stopModelAdapter = moshi.adapter(StopModel::class.java)
}