package com.dicoding.readroster.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailBook : Screen("home/{bookId}") {
        fun createRoute(bookId: Long) = "home/$bookId"
    }
}