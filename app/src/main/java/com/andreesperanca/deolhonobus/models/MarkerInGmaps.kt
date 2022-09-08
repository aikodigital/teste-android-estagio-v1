package com.andreesperanca.deolhonobus.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarkerInGmaps(
    val title: String,
    val listMarker: List<Place>
): Parcelable
