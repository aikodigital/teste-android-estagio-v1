package com.aiko.aikospvision.ui.componentes

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBar(navController: NavController) {
    val context = LocalContext.current
    val items = listOf(
        NavigationBarItems.Home,
        NavigationBarItems.Line,
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val isSelected = currentRoute?.startsWith(item.getGroup(context)) == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icons),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.getTitle(context),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                },
                selected = isSelected,
                onClick = {
                    try {
                        val oldGroup = currentRoute?.substringBefore("/") + "/"
                        val newGroup = item.getGroup(context)
                        val isSameGroup = items.any {
                            it.getGroup(context) == item.getGroup(context) && currentRoute?.startsWith(
                                it.getRoute(context)
                            ) == true
                        }
                        if (!isSameGroup) {
                            navController.navigate(item.getGroup(context) + item.getRoute(context))
                        }
                        if (oldGroup == newGroup) {
                            navController.navigate(item.getGroup(context) + item.getRoute(context))
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Exception: $e")
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}