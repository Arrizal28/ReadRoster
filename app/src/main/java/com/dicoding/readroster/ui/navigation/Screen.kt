package com.dicoding.readroster.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Cart : Screen("cart")
    data object Profile : Screen("profile")
    data object DetailBook : Screen("home/{bookId}") {
        fun createRoute(bookId: Long) = "home/$bookId"
    }
}