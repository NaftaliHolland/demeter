package com.mmust.demeter.presentation.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmust.demeter.presentation.login.LoginScreen
import com.mmust.demeter.presentation.login.LoginViewModel
import com.mmust.demeter.presentation.onboarding.OnBoardingScreen
import com.mmust.demeter.presentation.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: OnBoardingViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    var startDestination by remember { mutableStateOf(Route.Onboarding.route)}
    val hasCompletedOnboarding by viewModel.hasCompletedOnboarding.collectAsState()

    if (hasCompletedOnboarding) {
        startDestination = Route.Home.route
    }

    LaunchedEffect(startDestination) {
       Log.d("Start destination", "Start destination: ${startDestination}")
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Route.Onboarding.route) {
            OnBoardingScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = Route.Home.route) {
            Text(text = "Home meehn")
        }
        composable(route = Route.Login.route) {
            LoginScreen(loginViewModel)
        }
    }
}