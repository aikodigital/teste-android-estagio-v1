package com.aiko.aikospvision.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aiko.aikospvision.R
import com.aiko.aikospvision.ui.theme.AikoSPVisionTheme
import com.aiko.aikospvision.ui.theme.LightTextColor
import com.aiko.aikospvision.ui.theme.OrangeColor

@Composable
fun SearchInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "A onde quer ir?",
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(text = placeholderText, color = Color.Black) },
            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Black,
                disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                unfocusedContainerColor = Color.Black.copy(alpha = 0.1f),
                focusedContainerColor = Color.Black.copy(alpha = 0.1f),

                errorContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,

                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                errorTextColor = Color.Black,

                focusedSupportingTextColor = Color.Black,
                unfocusedSupportingTextColor = Color.Black,
                disabledSupportingTextColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        )
        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .background(OrangeColor, RoundedCornerShape(8.dp))
                .align(Alignment.CenterEnd)
                .size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchInputFieldPreview() {
    AikoSPVisionTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            SearchInputField(
                value = "campo de pesquisa",
                onValueChange = {},
                onSearchClick = {},
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

