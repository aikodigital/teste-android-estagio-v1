package com.hilguener.spbusao.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hilguener.spbusao.domain.model.Lines

@Composable
fun SearchLinesLayout(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    lines: List<Lines>?,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        CustomOutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = "Nome da Linha",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardController?.hide()
                onSearch()
            }),
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(lines ?: emptyList()) { line ->
                LineItem(line = line)
            }
        }
    }
}