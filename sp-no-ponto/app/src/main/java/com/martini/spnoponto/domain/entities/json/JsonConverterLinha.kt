package com.martini.spnoponto.domain.entities.json

import com.martini.spnoponto.domain.entities.lineSearch.Linha
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonConverterLinha {
    fun toString(linha: Linha): String {
        return Json.encodeToString(linha)
    }

    fun fromString(linhaString: String): Linha {
        return Json.decodeFromString(linhaString)
    }
}