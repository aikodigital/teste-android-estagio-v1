package com.martini.spnoponto.presentation.components.dashboard.appbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.spnoponto.domain.entities.settings.SetFilterSettingsParams
import com.martini.spnoponto.domain.usecases.GetFilterSettingUseCase
import com.martini.spnoponto.domain.usecases.SetFilterSettingsState
import com.martini.spnoponto.domain.usecases.SetFilterSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SetFilterSettingsViewModel @Inject constructor(
    private val setFilterSettingsUseCase: SetFilterSettingsUseCase,
    private val getFilterSettingUseCase: GetFilterSettingUseCase
) : ViewModel() {
    operator fun invoke(params: SetFilterSettingsParams) {
        setFilterSettingsUseCase(params)
            .flowOn(Dispatchers.IO)
            .onEach {
                if (it is SetFilterSettingsState.Loaded) {
                    reload()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun reload() {
        getFilterSettingUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}