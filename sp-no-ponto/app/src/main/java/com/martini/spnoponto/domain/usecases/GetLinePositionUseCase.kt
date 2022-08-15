package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.data.models.linePosition.toLinePosition
import com.martini.spnoponto.domain.entities.linePosition.LinePosition
import com.martini.spnoponto.domain.entities.linePosition.LinePositionParams
import com.martini.spnoponto.domain.repositories.TrafficRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class GetLinePositionUseCase @Inject constructor(
    private val trafficRepository: TrafficRepository
) {
    operator fun invoke(params: LinePositionParams) = flow {
        try {
            trafficRepository.authorize()
            val response = trafficRepository.getLinePosition(params)
            val positions = response.vs.map { it.toLinePosition() }
            emit(GetLinePositionState.Loaded(positions))
        } catch (e: HttpException) {
            emit(GetLinePositionState.ServerFailure)
        } catch (e: SocketTimeoutException) {
            emit(GetLinePositionState.TimeoutFailure)
        } catch (e: Exception) {
            emit(GetLinePositionState.Failure)
        }
    }
}

sealed class GetLinePositionState {
    object Initial : GetLinePositionState()
    object Loading : GetLinePositionState()
    class Loaded(
        val positions: List<LinePosition>
    ) : GetLinePositionState()

    object ServerFailure: GetLinePositionState()
    object TimeoutFailure : GetLinePositionState()
    object Failure : GetLinePositionState()
}