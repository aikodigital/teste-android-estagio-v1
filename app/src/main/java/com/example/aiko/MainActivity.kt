package com.example.aiko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.aiko.ui.theme.AikoTheme
import com.example.aiko.views.MapsApp
import com.example.aiko.views.PositionViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AikoTheme {
                MapsApp(viewModel = PositionViewModel())
            }
        }
    }
}
