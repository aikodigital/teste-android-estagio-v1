package com.aiko.aikospvision.ui.componentes

import android.content.Context
import androidx.annotation.DrawableRes
import com.aiko.aikospvision.R

sealed class NavigationBarItems(
    val titleResId: Int,
    val routeResId: Int,
    @DrawableRes val icons: Int,
    val groupResId: Int
) {
    fun getTitle(context: Context): String = context.getString(titleResId)
    fun getRoute(context: Context): String = context.getString(routeResId)
    fun getGroup(context: Context): String = context.getString(groupResId)

    object Home : NavigationBarItems(
        icons = R.drawable.direcction,
        titleResId = R.string.navigation_title_home,
        routeResId = R.string.navigation_route_home,
        groupResId = R.string.navigation_group_home
    )


    object Line : NavigationBarItems(
        icons = R.drawable.bus,
        titleResId = R.string.navigation_title_lines,
        routeResId = R.string.navigation_route_lines,
        groupResId = R.string.navigation_group_lines
    )
}
