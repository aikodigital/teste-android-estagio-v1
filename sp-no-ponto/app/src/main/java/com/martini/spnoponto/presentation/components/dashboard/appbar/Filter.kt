package com.martini.spnoponto.presentation.components.dashboard.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.entities.settings.SetFilterSettingsParams
import com.martini.spnoponto.domain.usecases.GetFilterSettingState

@ExperimentalMaterialApi
@Composable
fun DashboardAppBarFilter(
    getFilterSettingsViewModel: GetFilterSettingsViewModel = hiltViewModel(),
    setFilterSettingsViewModel: SetFilterSettingsViewModel = hiltViewModel()
) {
    val state = getFilterSettingsViewModel.state.value

    var currentFilter by remember {
        mutableStateOf(Filter.Todos)
    }

    var dialogIsOpen by remember {
        mutableStateOf(false)
    }

    fun closeDialog() {
        dialogIsOpen = false
    }

    fun openDialog() {
        dialogIsOpen = true
    }

    fun onClick(filter: Filter) {
        currentFilter = filter
    }

    fun onOk() {
        setFilterSettingsViewModel(
            SetFilterSettingsParams(currentFilter)
        )
        closeDialog()
    }

    val items = Filter.values()

    if (dialogIsOpen) {

        if (state is GetFilterSettingState.Loaded) {
            currentFilter = state.filter
        }

        AlertDialog(
            onDismissRequest = { closeDialog() },
            title = { Text(stringResource(R.string.dashboard_app_bar_alert_title)) },
            text = {
                Column {
                    items.forEach { filter ->
                        ListItem(
                            icon = {
                                RadioButton(
                                    selected = filter == currentFilter,
                                    onClick = { onClick(filter) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = if (MaterialTheme.colors.isLight) {
                                            MaterialTheme.colors.primary
                                        } else {
                                            MaterialTheme.colors.secondary
                                        }
                                    )
                                )
                            },
                            text = { Text(filter.name) }
                        )
                    }
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { closeDialog() }) {
                        Text(stringResource(R.string.dashboard_app_bar_alert_cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onOk() }) {
                        Text(stringResource(R.string.dashboard_app_bar_alert_ok))
                    }
                }
            }
        )
    }

    IconButton(onClick = { openDialog() }) {
        Icon(
            Icons.Filled.FilterList,
            contentDescription = stringResource(R.string.dashboard_app_bar_filter)
        )
    }
}