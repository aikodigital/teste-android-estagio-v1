package com.andreesperanca.deolhonobus.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val title: String,
    val lng: LatLng
): Parcelable
