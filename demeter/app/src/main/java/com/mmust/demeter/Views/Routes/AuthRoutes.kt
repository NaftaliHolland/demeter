package com.mmust.demeter.Views.Routes

sealed class AuthRoutes(val route: String) {
    data object SignUp : AuthRoutes("signup")
    data object Login : AuthRoutes("login")
}