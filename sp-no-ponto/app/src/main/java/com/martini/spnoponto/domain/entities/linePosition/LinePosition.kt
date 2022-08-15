package com.martini.spnoponto.domain.entities.linePosition


data class LinePosition(
    val disabledFriendly: Boolean,
    val prefix: String,
    val longitude: Double,
    val latitude: Double,
    val date: String
)
