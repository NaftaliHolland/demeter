package com.mmust.demeter.presentation.navigation

sealed class Route(val route: String) {
    object Onboarding: Route("onboarding_screen")
    object Home: Route("home_screen")
    object Login: Route("login_screen")
    object SignUp: Route("sign_up_screen")
}