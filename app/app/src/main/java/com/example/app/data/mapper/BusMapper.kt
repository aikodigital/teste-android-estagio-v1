package com.example.app.data.mapper

import com.example.app.data.model.AllLinesResponse
import com.example.app.data.model.BusResponse
import com.example.app.data.model.BusWithTimeResponse
import com.example.app.data.model.LineResponse
import com.example.app.domain.model.AllLines
import com.example.app.domain.model.Bus
import com.example.app.domain.model.BusWithTime
import com.example.app.domain.model.Line

fun AllLinesResponse.toDomain() = AllLines(
    requestHour = this.requestHour ?: "",
    linesRelation = this.linesRelation?.map { it?.toDomain() } ?: emptyList()
)

fun LineResponse.toDomain() = Line(
    fullPlacard = this.fullPlacard ?: "",
    lineCode = this.lineCode ?: 0,
    lineDirection = this.lineDirection ?: 0,
    lineDestination = this.lineDestination ?: "",
    lineOrigin = this.lineOrigin ?: "",
    numberBuses = this.numberBuses ?: 0,
    busList = this.busList?.map { it?.toDomain() } ?: emptyList()
)

fun BusResponse.toDomain() = Bus(
    busPrefix = this.busPrefix ?: 0,
    isAccessible = this.isAccessible ?: false,
    utcRequestHour = this.utcRequestHour ?: "",
    lat = this.lat ?: 0.0,
    lng = this.lng ?: 0.0,
    sv = this.sv ?: false,
    `is` = this.`is` ?: false
)

fun BusWithTimeResponse.toDomain() = BusWithTime(
    busPrefix = this.busPrefix ?: 0,
    arrivalForecast = this.arrivalForecast ?: "",
    isAccessible = this.isAccessible ?: false,
    utcRequestHour = this.utcRequestHour ?: "",
    lat = this.lat ?: 0.0,
    lng = this.lng ?: 0.0
)