package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.data.models.line.toLinha
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import com.martini.spnoponto.domain.entities.lineSearch.SearchLineParams
import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.repositories.TrafficRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class SearchLineUseCase @Inject constructor(
    private val trafficRepository: TrafficRepository
) {
    operator fun invoke(params: SearchLineParams) = flow {
        try {
            emit(SearchLineState.Loading)
            trafficRepository.authorize()

            val filter = trafficRepository.getFilterSettings()

            val result = trafficRepository.searchLine(params)

            val lines = when(filter) {
                Filter.Primario -> {
                    result.map { it.toLinha() }
                        .filter { it.way == Constants.goingToPrimaryTerminal }
                }
                Filter.Secundario -> {
                    result.map { it.toLinha() }
                        .filter { it.way == Constants.goingToSecondaryTerminal }
                }
                else -> result.map { it.toLinha() }
            }

            emit(SearchLineState.Loaded(lines))
        } catch (e: HttpException) {
            emit(SearchLineState.ServerFailure)
        } catch (e: SocketTimeoutException) {
            emit(SearchLineState.TimeoutFailure)
        } catch (e: Exception) {
            emit(SearchLineState.Failure)
        }
    }
}

sealed class SearchLineState {
    object Initial : SearchLineState()
    object Loading : SearchLineState()
    class Loaded(
        val lines: List<Linha>
    ) : SearchLineState()

    object ServerFailure : SearchLineState()
    object TimeoutFailure : SearchLineState()
    object Failure : SearchLineState()
}