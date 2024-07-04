package com.example.mapapp.ui.MyClass

data class ForecastResponse(
    // Horário de referência da geração das informações
    val hr: String,
    // Representa um ponto de parada onde:
    val p: StopDetails
)
