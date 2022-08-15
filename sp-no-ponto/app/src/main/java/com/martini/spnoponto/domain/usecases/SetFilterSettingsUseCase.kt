package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.domain.entities.settings.SetFilterSettingsParams
import com.martini.spnoponto.domain.repositories.TrafficRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetFilterSettingsUseCase @Inject constructor(
    private val trafficRepository: TrafficRepository
) {
    operator fun invoke(params: SetFilterSettingsParams) = flow {
        try {
            emit(SetFilterSettingsState.Loading)

            trafficRepository.setFilterSettings(params)

            emit(SetFilterSettingsState.Loaded)

        } catch (e: Exception) {
            emit(SetFilterSettingsState.Failure)
        }
    }
}

sealed class SetFilterSettingsState {
    object Loading: SetFilterSettingsState()
    object Loaded: SetFilterSettingsState()
    object Failure: SetFilterSettingsState()
}