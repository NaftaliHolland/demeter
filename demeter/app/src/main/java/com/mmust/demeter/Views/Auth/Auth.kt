package com.mmust.demeter.Views.Auth

import com.mmust.demeter.ViewModels.Auth.AuthViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Auth(mainNav : NavController, vm : AuthViewModel,){
    val authNav = rememberNavController()
    NavHost(
        navController = authNav,
        startDestination = "login"
    ){
        composable("login") { Login(mainNav,vm,authNav) }
        composable("signup") { SignUp(mainNav,vm,  authNav) }
    }
}