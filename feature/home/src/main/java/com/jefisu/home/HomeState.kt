package com.jefisu.home

import com.jefisu.domain.model.Line

data class HomeState(
    val searchBusNumber: String = "",
    val busLines: List<Line> = emptyList(),
    val isLoading: Boolean = false,
    val error: com.jefisu.common.UiText? = null,
    val isOffline: Boolean = false
)