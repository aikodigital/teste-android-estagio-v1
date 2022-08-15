package com.martini.spnoponto.data.dataSources.local

import android.content.SharedPreferences
import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.entities.settings.SetFilterSettingsParams
import javax.inject.Inject

class SettingsLocalDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun getFilterSettings(): Filter {
        return when(prefs.getString(Constants.filterPrefsKey, null)) {
            Filter.Primario.name -> Filter.Primario
            Filter.Secundario.name -> Filter.Secundario
            else -> Filter.Todos
        }
    }

    fun setFilterSettings(params: SetFilterSettingsParams) {
        with(prefs.edit()) {
            putString(Constants.filterPrefsKey, params.filter.name)
            commit()
        }
    }
}