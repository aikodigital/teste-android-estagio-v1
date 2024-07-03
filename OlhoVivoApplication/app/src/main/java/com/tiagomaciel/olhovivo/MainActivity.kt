package com.tiagomaciel.olhovivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tiagomaciel.olhovivo.api.dataClass.VehiclePosition
import com.tiagomaciel.olhovivo.screens.VehiclePositionsMapScreen
import com.tiagomaciel.olhovivo.ui.theme.OlhoVivoApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var items by remember {
                mutableStateOf<VehiclePosition?>(null)
            }
            OlhoVivoApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    VehiclePositionsMapScreen()
                }
            }
        }
    }
}

