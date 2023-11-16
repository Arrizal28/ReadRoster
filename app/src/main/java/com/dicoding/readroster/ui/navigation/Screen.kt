package com.dicoding.readroster.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailBook : Screen("home/{rewardId}") {
        fun createRoute(rewardId: Long) = "home/$rewardId"
    }
}