package com.martini.spnoponto.presentation.components.dashboard.appbar

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.entities.lineSearch.SearchLineParams
import com.martini.spnoponto.presentation.components.dashboard.line.SearchLineViewModel

@Composable
fun DashboardSearchField(
    searchLineViewModel: SearchLineViewModel = hiltViewModel()
) {
    var text by remember {
        mutableStateOf("")
    }

    var isError by remember {
        mutableStateOf(false)
    }

    TextField(
        isError = isError,
        value = text,
        onValueChange = {
            text = it
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (text.isEmpty() || text.length < 3) {
                    isError = true
                    return@KeyboardActions
                }
                isError = false
                searchLineViewModel(SearchLineParams(text))
            }
        ),
        maxLines = 1,
        placeholder = {
            Text(
                stringResource(R.string.dashboard_app_bar_search_line), style = TextStyle(
                    color = Color.White,
                )
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.typography.body2.color,
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                Icons.Filled.Search, stringResource(R.string.dashboard_app_bar_search),
                tint = Color.White.copy(0.8f),
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { text = "" }) {
                    Icon(
                        Icons.Filled.Clear, stringResource(R.string.dashboard_app_bar_clear),
                        tint = Color.White.copy(0.8f),
                    )
                }
            }
        }
    )
}