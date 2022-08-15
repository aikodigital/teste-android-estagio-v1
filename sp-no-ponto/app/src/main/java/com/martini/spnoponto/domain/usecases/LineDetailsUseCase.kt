package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.common.InvalidLineDetailsArgumentException
import com.martini.spnoponto.domain.entities.json.JsonConverterLinha
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LineDetailsUseCase @Inject constructor(
    private val jsonConverterLinha: JsonConverterLinha
) {

    val listen = MutableSharedFlow<LineDetailsState>(0)

    operator fun invoke(linha: String?) = flow {
        try {
            emit(LineDetailsState.Loading)
            listen.emit(LineDetailsState.Loading)
            if (linha == null) throw InvalidLineDetailsArgumentException()
            val line: Linha = jsonConverterLinha.fromString(linha)
            emit(LineDetailsState.Loaded(line))
            listen.emit(LineDetailsState.Loaded(line))
        } catch (e: InvalidLineDetailsArgumentException) {
            emit(LineDetailsState.InvalidArgumentFailure)
            listen.emit(LineDetailsState.InvalidArgumentFailure)
        } catch (e: Exception) {
            emit(LineDetailsState.Failure)
            listen.emit(LineDetailsState.Failure)
        }
    }
}

sealed class LineDetailsState {
    object Initial : LineDetailsState()
    object Loading : LineDetailsState()
    class Loaded(
        val linha: Linha
    ) : LineDetailsState()

    object InvalidArgumentFailure : LineDetailsState()
    object Failure : LineDetailsState()
}