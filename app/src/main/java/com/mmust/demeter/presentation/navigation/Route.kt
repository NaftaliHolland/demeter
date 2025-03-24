package com.mmust.demeter.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(val route: String, val icon: ImageVector? = null, val label: String = "") {
    object Onboarding: Route("onboarding_screen")
    object Login: Route("login_screen")
    object SignUp: Route("sign_up_screen")
    object Greenhouse: Route("greenhouse_screen/{id}") {
        fun createRoute(id: String) = "greenhouse_screen/$id"
    }
    object Home: Route("home_screen", Icons.Default.Home, "Home")
    object Profile: Route("profile", Icons.Default.Person, "Profile")
    object Settings: Route("settings", Icons.Default.Settings, "Settings")

    companion object {
        val bottomNavItems = listOf(Home, Profile, Settings)
    }
}