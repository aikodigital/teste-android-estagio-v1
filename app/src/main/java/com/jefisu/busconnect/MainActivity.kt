package com.jefisu.busconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jefisu.busconnect.navigation.NavigationGraph
import com.jefisu.home.HomeScreenRoute
import com.jefisu.ui.theme.BusConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            BusConnectTheme {
                NavigationGraph(startRoute = HomeScreenRoute)
            }
        }
    }
}
