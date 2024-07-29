package com.devandroid.test_aiko.models

data class PositionResponse(
    val hr : String, // time position
    val l : List<Line> // list line
)
