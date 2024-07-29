package com.devandroid.test_aiko.models

data class Line(
    val cl: Int, // line code
    val lc: Boolean,  // if line is operating
    val lt: String,  // firts part of letter
    val tl: Int, // number of service
    val sl: Int, // way line
    val tp: String,  // letter
    val ts: String,// Letter
    val vs : List<Vehicle>
)