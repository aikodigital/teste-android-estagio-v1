package com.aiko.aikospvision.ui.screens_app.bus_lines

import com.aiko.domain.model.LineModel

data class BusLineState(
    val inputSearch: String? = null,
    val busLineList: List<LineModel>? = null,
)