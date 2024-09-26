package com.aiko.aikospvision.ui.screens_app.home

import com.aiko.domain.model.StopModel

data class HomeState(
    val searchText: String = "",
    val listStopped: List<StopModel> = emptyList(),
    val onSearchClick: () -> Unit = {},
    val listFavorites: List<String> = emptyList(),
)