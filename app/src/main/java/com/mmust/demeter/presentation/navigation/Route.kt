package com.mmust.demeter.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.bumptech.glide.load.resource.drawable.DrawableResource
import com.mmust.demeter.R

sealed class Route(val route: String, @DrawableRes val icon: Int? = null, val label: String = "") {
    object Onboarding: Route("onboarding_screen")
    object Login: Route("login_screen")
    object SignUp: Route("sign_up_screen")
    object Greenhouse: Route("greenhouse_screen/{id}") {
        fun createRoute(id: String) = "greenhouse_screen/$id"
    }
    object Home: Route("home_screen", R.drawable.ic_soil, "Home")
    //object Profile: Route("profile", Icons.Default.Person, "Profile")
    object Alert: Route("alerts", R.drawable.ic_soil, "Alerts")
    object Report: Route("report", R.drawable.report, "reports")
    object Settings: Route("settings", R.drawable.ic_humidity, "Settings")

    companion object {
        val bottomNavItems = listOf(Home, Report, Alert, Settings)
    }
}