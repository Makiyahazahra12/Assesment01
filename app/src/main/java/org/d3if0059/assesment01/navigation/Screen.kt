package org.d3if0059.assesment01.navigation

sealed class Screen(val route:String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("AboutScreen")
}