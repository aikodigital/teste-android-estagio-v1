package com.martini.spnoponto.presentation.screens

import com.martini.spnoponto.constants.Constants

sealed class Screen(val route: String) {
    object Dashboard: Screen("Dashboard")
    object LineDetails: Screen("LineDetails/{${Constants.LINE_DETAILS_NAV_ARG}}") {
        fun passLine(line: String): String {
            return "LineDetails/$line"
        }
    }
}
