package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.repositories.TrafficRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilterSettingUseCase @Inject constructor(
    private val trafficRepository: TrafficRepository
) {

    val listen = MutableSharedFlow<GetFilterSettingState>(0)

    operator fun invoke() = flow {
        try {
            emit(GetFilterSettingState.Loading)
            listen.emit(GetFilterSettingState.Loading)

            val filter = trafficRepository.getFilterSettings()

            listen.emit(GetFilterSettingState.Loaded(filter))
            emit(GetFilterSettingState.Loaded(filter))
        } catch (e: Exception) {
            listen.emit(GetFilterSettingState.Failure)
            emit(GetFilterSettingState.Failure)
        }
    }
}

sealed class GetFilterSettingState {
    object Loading: GetFilterSettingState()
    class Loaded(
        val filter: Filter
    ): GetFilterSettingState()
    object Failure: GetFilterSettingState()
}