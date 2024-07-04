package com.example.app.data.mapper

import com.example.app.data.model.StopPointResponse
import com.example.app.domain.model.StopPoint

fun StopPointResponse.toDomain() = StopPoint(
    stopPointCode = this.stopPointCode ?: 0,
    stopPointName = this.stopPointName ?: "",
    stopPointAddress = this.stopPointAddress ?: "",
    lat = this.lat ?: 0.0,
    lng = this.lng ?: 0.0
)

fun List<StopPointResponse>.toDomain(): List<StopPoint> {
    return this.map { it.toDomain() }
}
