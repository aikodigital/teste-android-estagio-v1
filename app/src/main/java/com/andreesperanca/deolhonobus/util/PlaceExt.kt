package com.andreesperanca.deolhonobus.util

import com.andreesperanca.deolhonobus.models.Place

fun Place.toList() : List<Place> {
    val placeList = mutableListOf<Place>()
    placeList.add(Place(title = this.title, lng = this.lng))
    return placeList
}