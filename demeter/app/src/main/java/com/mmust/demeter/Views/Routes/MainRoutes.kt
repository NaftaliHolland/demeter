package com.mmust.demeter.Views.Routes

sealed class MainRoutes(val route: String) {
    object Auth : MainRoutes("auth")
    object Home : MainRoutes("home")
    object Profile : MainRoutes("profile")
    object AddGreenhouse : MainRoutes("addgreenhouse")
}